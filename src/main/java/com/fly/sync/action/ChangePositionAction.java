package com.fly.sync.action;

import com.fly.sync.contract.AbstractAction;
import com.fly.sync.contract.DbFactory;
import com.fly.sync.setting.BinLog;
import com.fly.sync.setting.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangePositionAction implements AbstractAction {
    public final static Logger logger = LoggerFactory.getLogger(ChangePositionAction.class);
    private BinLog.Position binLogPosition;

    public ChangePositionAction(BinLog.Position binLogPosition) {
        this.binLogPosition = binLogPosition;
    }

    public ChangePositionAction(String name, long position) {
        binLogPosition = new BinLog.Position(name, position);
    }

    public static ChangePositionAction create(BinLog.Position binLogPosition) {
        return new ChangePositionAction(binLogPosition);
    }

    public static ChangePositionAction create(String name, long position) {
        return new ChangePositionAction(name, position);
    }

    @Override
    public String getGroup() {
        return ChangePositionAction.class.getName();
    }

    @Override
    public void execute(DbFactory dbFactory) {

        try
        {
            Setting.binLog.set(dbFactory.getRiverDatabase().schemaName, binLogPosition);
            Setting.saveBinLog();
        } catch (Exception e)
        {
            logger.error("Save BinLog Position Failed.", e);
        }
    }
}
