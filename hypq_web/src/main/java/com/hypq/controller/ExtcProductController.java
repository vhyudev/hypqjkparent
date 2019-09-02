package com.hypq.controller;

import cn.hypq.utils.Page;
import com.hypq.domain.Contract;

import com.hypq.domain.ContractProduct;
import com.hypq.domain.ExtCproduct;
import com.hypq.domain.Factory;

import com.hypq.service.ContractProductService;
import com.hypq.service.ContractService;
import com.hypq.service.ExtCproductService;
import com.hypq.service.FactoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/extcargo")
public class ExtcProductController {

    @Autowired
    FactoryService factoryService;
    @Autowired
    ExtCproductService extCProductService;
    @Autowired
    ContractService contractService;
    @Autowired
    ContractProductService contractProductService;
    @RequestMapping("extCproductAction_tocreate")
    public String extCProductAction_tocreate(Model mode, String pagenum, ExtCproduct ep, HttpServletRequest request){
        if( StringUtils.isBlank(pagenum)){
            pagenum="1";
        }
        ContractProduct byId = contractProductService.findById(ep.getContractProduct().getId());
        mode.addAttribute("contractproduct",byId);
        Page<ExtCproduct> page=extCProductService.findByPage(pagenum,ep);
        page.setUrl("/extcargo/extCproductAction_tocreate");
         List<Factory> factorylist = factoryService.findAll();
         request.setAttribute("factoryList",factorylist);
        mode.addAttribute("results",page.getResults());
        mode.addAttribute("links",page.getLinks());
        return "cargo/contract/jExtCproductCreate";
    }



    @RequestMapping("extCproductAction_insert")
    public String extCProductAction_insert(Model mode,ExtCproduct cp){
        double total = cp.getCnumber() * cp.getPrice();
        cp.setAmount(total);
        Contract byId = contractService.findById(cp.getContractProduct().getContract().getId());
        Double totalAmount = byId.getTotalAmount()+total;

        byId.setTotalAmount(totalAmount);
        contractService.createContract(byId);
        extCProductService.createExtCproduct(cp);

        return "redirect:extCproductAction_tocreate?contractProduct.id="+cp.getContractProduct().getId();
    }

    @RequestMapping("extCproductAction_delete")
    public String extCProductAction_delete(ExtCproduct ep){
        ExtCproduct cp = extCProductService.findById(ep.getId());

        Contract contract = contractService.findById(ep.getContractProduct().getContract().getId());
        contract.setTotalAmount(contract.getTotalAmount()-cp.getAmount());
        extCProductService.deleteById(ep.getId());
        contractService.createContract(contract);


        return "redirect:extCproductAction_tocreate?contractProduct.id="+cp.getContractProduct().getId();
    }


    @RequestMapping("extCproductAction_toupdate")
    public String extCProductAction_toupdate(Model mode,String id ){
        ExtCproduct cp = extCProductService.findById(id);
        List<Factory> factorylist = factoryService.findAll();
        mode.addAttribute("cp",cp);
        mode.addAttribute("factorylist",factorylist);
        return "cargo/contract/jExtCproductUpdate";
    }


  @RequestMapping("extCproductAction_update")
    public String extCProductAction_update(ExtCproduct extCProduct ){
        ExtCproduct cp = extCProductService.findById(extCProduct.getId());
        Contract contract = contractService.findById(extCProduct.getContractProduct().getContract().getId());
        contract.setTotalAmount(extCProduct.getCnumber()*extCProduct.getPrice()-cp.getAmount()+contract.getTotalAmount());
        extCProduct.setAmount(extCProduct.getCnumber()*extCProduct.getPrice());
        BeanUtils.copyProperties(extCProduct,cp);
        contractService.createContract(contract);
        extCProductService.createExtCproduct(cp);
        return "redirect:extCproductAction_tocreate?contractProduct.id="+cp.getContractProduct().getId();
    }
}
