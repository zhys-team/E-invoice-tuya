/**
 * 
 */
package top.anets.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import top.anets.utils.InvoiceUtils;
import top.anets.utils.Result;
import top.anets.vo.BusinessVo;

import top.anets.service.SapService;

/**
 * @author Administrator
 *
 */
@Component
public class Sap {
	@Autowired
	private SapService sapService;
	
	@Scheduled(initialDelay=5555, fixedRate=1500)
    public void fetchInvoices() {
//    	1从sap取发票
//		sapService.revoke("2000", "1","1");
//		sapService.revoke("2100", "1","1");
		BusinessVo businessVo = new BusinessVo();
        businessVo.setOrgId("2000");
        businessVo.setOrgMachine("00");
		if(InvoiceUtils.isStartedInvoice(businessVo)) {
			sapService.revoke("2000", "1","1");
   		}
		
		
		BusinessVo businessVo1 = new BusinessVo();
        businessVo1.setOrgId("2100");
        businessVo1.setOrgMachine("00");
		if(InvoiceUtils.isStartedInvoice(businessVo1)) {
			sapService.revoke("2100", "1","1");
   		}
    }
}
