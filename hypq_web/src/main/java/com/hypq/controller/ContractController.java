package com.hypq.controller;

import cn.hypq.utils.Page;
import com.hypq.domain.Contract;
import com.hypq.service.ContractService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/cargo")
public class ContractController {
    @Autowired
    ContractService contractService;

    //分页查询部门信息
    @RequestMapping("/contractAction_list")
    public String contractAction_list(Model mode,String pagenum){
        if( StringUtils.isBlank(pagenum)){
            pagenum="1";
        }
        Page<Contract> page=contractService.findByPage(pagenum);
        page.setUrl("/cargo/contractAction_list");
        mode.addAttribute("results",page.getResults());
        mode.addAttribute("links",page.getLinks());
        return "cargo/contract/jContractList";
    }

    //查看部门详情
    @RequestMapping("/contractAction_toview")
    public String contractAction_toview(Model model,String id){
       Contract contract= contractService.findById(id);
        model.addAttribute("contract",contract);
        return "cargo/contract/jContractView";
    }

    //到新增部门页面 contractAction_tocreate
    @RequestMapping("/contractAction_tocreate")
    public String contractAction_tocreate(Model model){


        return "cargo/contract/jContractCreate";
    }
    //新增 contractAction_insert
    @RequestMapping("/contractAction_insert")
    public String contractAction_insert(Contract contract){
        contract.setTotalAmount(0d);
        contractService.createContract(contract);

        return "redirect:contractAction_list";
    }

    //新增 contractAction_insert
    @RequestMapping("/contractAction_update")
    public String contractAction_update(Contract contract){
        contractService.createContract(contract);

        return "redirect:contractAction_list";
    }

  //部门修改  contractAction_toupdate
  @RequestMapping("/contractAction_toupdate")
  public String contractAction_toupdate(Model model ,String id ){
      Contract byId = contractService.findById(id);
      model.addAttribute("contract",byId);
      return "cargo/contract/jContractUpdate";
  }
    //部门删除 contractAction_delete  递归删除
    @RequestMapping("/contractAction_delete")
    public String contractAction_delete(String id){
       deleteAll(id);
        return "redirect:contractAction_list";
    }
    public void deleteAll(String id){

        contractService.deleteById( id);
    }


    @RequestMapping("/contractAction_submit")
    public String contractAction_submit(String[] id ){
      for(String ids:id){
          Contract byId = contractService.findById(ids);
          byId.setState(1);
           contractService.createContract(byId);
      }

        return "redirect:contractAction_list";
    }

    @RequestMapping("/contractAction_cancel")
    public String contractAction_cancel(String[] id ){
        for(String ids:id){
            Contract byId = contractService.findById(ids);
            byId.setState(0);
            contractService.createContract(byId);
        }

        return "redirect:contractAction_list";
    }
}
