package com.hypq.controller;

import cn.hypq.utils.Page;
import com.hypq.domain.Contract;
import com.hypq.domain.ContractProduct;
import com.hypq.domain.Factory;
import com.hypq.service.ContractProductService;
import com.hypq.service.ContractService;
import com.hypq.service.FactoryService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.smartcardio.SunPCSC;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cargopdc")
public class ContractProductController {
    @Autowired
    FactoryService factoryService;
    @Autowired
    ContractProductService contractProductService;
    @Autowired
    ContractService contractService;
    @RequestMapping("contractProductAction_tocreate")
    public String contractProductAction_tocreate(Model mode, String pagenum, ContractProduct cp, HttpServletRequest request){
        if( StringUtils.isBlank(pagenum)){
            pagenum="1";
        }
        Contract byId = contractService.findById(cp.getContract().getId());
        mode.addAttribute("contract",byId);
        Page<ContractProduct> page=contractProductService.findByPage(pagenum,cp);
        page.setUrl("/cargopdc/contractProductAction_tocreate");
         List<Factory> factorylist = factoryService.findAll();
         request.setAttribute("factoryList",factorylist);
        mode.addAttribute("results",page.getResults());
        mode.addAttribute("links",page.getLinks());
        return "cargo/contract/jContractProductCreate";
    }



    @RequestMapping("contractProductAction_insert")
    public String contractProductAction_insert(Model mode,ContractProduct cp){
        double total = cp.getCnumber() * cp.getPrice();
        cp.setAmount(total);
        Contract byId = contractService.findById(cp.getContract().getId());
        Double totalAmount = byId.getTotalAmount()+total;

        byId.setTotalAmount(totalAmount);
        contractService.createContract(byId);
        contractProductService.createContractProduct(cp);

        return "redirect:contractProductAction_tocreate?contract.id="+cp.getContract().getId();
    }

    @RequestMapping("contractProductAction_delete")
    public String contractProductAction_delete(Model mode,String id ,String contractid){
        ContractProduct cp = contractProductService.findById(id);

        Contract contract = contractService.findById(contractid);
        contract.setTotalAmount(contract.getTotalAmount()-cp.getAmount());
        contractProductService.deleteById(id);
        contractService.createContract(contract);


        return "redirect:contractProductAction_tocreate?contract.id="+contractid;
    }


    @RequestMapping("contractProductAction_toupdate")
    public String contractProductAction_toupdate(Model mode,String id ){
        ContractProduct cp = contractProductService.findById(id);
        List<Factory> factorylist = factoryService.findAll();
        mode.addAttribute("cp",cp);
        mode.addAttribute("factorylist",factorylist);
        return "cargo/contract/jContractProductUpdate";
    }


    @RequestMapping("contractProductAction_update")
    public String contractProductAction_update(ContractProduct contractProduct ){
        ContractProduct cp = contractProductService.findById(contractProduct.getId());
        Contract contract = contractService.findById(contractProduct.getContract().getId());
        contract.setTotalAmount(contractProduct.getCnumber()*contractProduct.getPrice()-cp.getAmount()+contract.getTotalAmount());
        contractProduct.setAmount(contractProduct.getCnumber()*contractProduct.getPrice());
        BeanUtils.copyProperties(contractProduct,cp);
        contractService.createContract(contract);
        contractProductService.createContractProduct(cp);
        return "redirect:contractProductAction_tocreate?contract.id="+contractProduct.getContract().getId();
    }
}
