package org.akaza.openclinica.domain.rule.action;

import org.akaza.openclinica.bean.login.UserAccountBean;
import org.akaza.openclinica.bean.managestudy.StudyBean;
import org.akaza.openclinica.domain.rule.RuleSetBean;
import org.akaza.openclinica.logic.rulerunner.ExecutionMode;
import org.akaza.openclinica.logic.rulerunner.RuleRunner.RuleRunnerMode;
import org.akaza.openclinica.service.crfdata.DynamicsMetadataService;

import javax.sql.DataSource;

public class ShowActionProcessor implements ActionProcessor {

    DataSource ds;
    DynamicsMetadataService itemMetadataService;
    RuleSetBean ruleSet;

    public ShowActionProcessor(DataSource ds, DynamicsMetadataService itemMetadataService, RuleSetBean ruleSet) {
        this.itemMetadataService = itemMetadataService;
        this.ruleSet = ruleSet;
        this.ds = ds;
    }

    public RuleActionBean execute(RuleRunnerMode ruleRunnerMode, ExecutionMode executionMode, RuleActionBean ruleAction, int itemDataBeanId, String itemData,
            StudyBean currentStudy, UserAccountBean ub, Object... arguments) {

        switch (executionMode) {
        case DRY_RUN: {
            if (ruleRunnerMode == RuleRunnerMode.DATA_ENTRY) {
                return null;
            } else {
                dryRun(ruleAction, itemDataBeanId, itemData, currentStudy, ub);
            }
        }
        case SAVE: {
            if (ruleRunnerMode == RuleRunnerMode.DATA_ENTRY) {
                return saveAndReturnMessage(ruleAction, itemDataBeanId, itemData, currentStudy, ub);
            } else {
                return save(ruleAction, itemDataBeanId, itemData, currentStudy, ub);
            }
        }
        default:
            return null;
        }
    }

    private RuleActionBean save(RuleActionBean ruleAction, int itemDataBeanId, String itemData, StudyBean currentStudy, UserAccountBean ub) {
        getItemMetadataService().showNew(itemDataBeanId, ((ShowActionBean) ruleAction).getProperties(), ub, ruleSet);
        return ruleAction;
    }

    private RuleActionBean saveAndReturnMessage(RuleActionBean ruleAction, int itemDataBeanId, String itemData, StudyBean currentStudy, UserAccountBean ub) {
        getItemMetadataService().showNew(itemDataBeanId, ((ShowActionBean) ruleAction).getProperties(), ub, ruleSet);
        return ruleAction;
    }

    private RuleActionBean dryRun(RuleActionBean ruleAction, int itemDataBeanId, String itemData, StudyBean currentStudy, UserAccountBean ub) {
        return ruleAction;
    }

    private DynamicsMetadataService getItemMetadataService() {
        return itemMetadataService;
    }

}
