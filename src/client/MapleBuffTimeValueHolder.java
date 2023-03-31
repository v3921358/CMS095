/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author wubin
 */
public class MapleBuffTimeValueHolder {

    public int skillId;
    public long startTime;
    public long length;

    public MapleBuffTimeValueHolder(int skillId, long startTime, long length) {
        super();
        this.skillId = skillId;
        this.startTime = startTime;
        this.length = length;
    }
}
