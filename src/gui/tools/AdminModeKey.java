/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.tools;

/**
 *
 * @author Lvkejian-PC
 */
public enum AdminModeKey {
    
    开发调试模式("335860914"),
    仅管理员帐号登录("335860914"),
    加载技能检测("335860914"),
    周榜执行更新("335860914"),
    封包调试模块("335860914"),
    记录服务端发送封包("335860914"),
    记录客户端发送封包("335860914"),
    数据转换模块("335860914"),
    游戏更新工具("335860914"),
    管理奖励事件("335860914"),
    管理活动列表("335860914"),
    V矩阵启用状态("335860914"),
    强制退出("335860914"),
    重载技能("335860914"),

    ;

    public final String Key;

    private AdminModeKey(final String Key) {
        this.Key = Key;
    }
}
