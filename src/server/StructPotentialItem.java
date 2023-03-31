package server;

public class StructPotentialItem {
    public byte incSTR, incDEX, incINT, incLUK, incACC, incEVA, incSpeed, incJump,
            incPAD, incMAD, incPDD, incMDD, prop, time, incSTRr, incDEXr, incINTr,
            incLUKr, incMHPr, incMMPr, incACCr, incEVAr, incPADr, incMADr, incPDDr,
            incMDDr, incCr, incDAMr, RecoveryHP, RecoveryMP, HP, MP, level,
            ignoreTargetDEF, ignoreDAM, DAMreflect, mpconReduce, mpRestore,
            incMesoProp, incRewardProp, incAllskill, ignoreDAMr, RecoveryUP;
    public boolean boss;
    public short incMHP, incMMP, attackType, skillID;
    public int optionType, reqLevel, potentialID; //probably the slot
    public String face; //angry, cheers, love, blaze, glitter

    @Override
    public final String toString() {
        final StringBuilder ret = new StringBuilder();
        if (incMesoProp > 0) {
            ret.append("无法得知潜在能力。");
            //ret.append(incMesoProp);
            //ret.append(" ");
        }
        if (incRewardProp > 0) {
            ret.append("无法得知潜在能力。");
            //ret.append(incRewardProp);
            //ret.append(" ");
        }
        if (prop > 0) {
            ret.append("概率 : ");
            ret.append(prop);
            ret.append("% ");
        }
        if (time > 0) {
            ret.append("時间 : ");
            ret.append(time);
            ret.append(" ");
        }
        if (attackType > 0) {
            ret.append("攻击类型 : ");
            ret.append(attackType);
            ret.append(" ");
        }
        if (incAllskill > 0) {
            ret.append("所有技能等級 : +");
            ret.append(incAllskill);
            ret.append(" ");
        }
        if (skillID > 0) {
            ret.append("Gives SKILL: ");
            ret.append(skillID);
            ret.append(" ");
        }
        if (boss) {
            ret.append("boss伤害, ");
        }
        /*if (face.length() > 0) {
            ret.append("Face Expression: ");
            ret.append(face);
            ret.append(" ");
        }*/
        if (RecoveryUP > 0) {
            ret.append("HP恢復道具丶hp恢復技能效率 : +");
            ret.append(RecoveryUP);
            ret.append("% ");
        }
        if (DAMreflect > 0) {
            ret.append("无法得知潜在能力。");
            ret.append(DAMreflect);
            ret.append(" ");
        }
        if (mpconReduce > 0) {
            ret.append("无法得知潜在能力。");
            ret.append(mpconReduce);
            ret.append(" ");
        }
        if (ignoreTargetDEF > 0) {
            ret.append("无視怪物防禦 : +");
            ret.append(ignoreTargetDEF);
            ret.append("% ");
        }
        if (RecoveryHP > 0) {
            ret.append("Hp恢復 : +");
            ret.append(RecoveryHP);
            ret.append(" ");
        }
        if (RecoveryMP > 0) {
            ret.append("Mp恢復 : +");
            ret.append(RecoveryMP);
            ret.append(" ");
        }
        if (HP > 0) { //no idea
            ret.append("Hp恢復 : +");
            ret.append(HP);
            ret.append(" ");
        }
        if (MP > 0) { //no idea
            ret.append("Mp恢復 : +");
            ret.append(MP);
            ret.append(" ");
        }
        if (mpRestore > 0) { //no idea
            ret.append("无法得知潜在能力");
            ret.append(mpRestore);
            ret.append(" ");
        }
        if (ignoreDAM > 0) {
            ret.append("被攻击時无視伤害 : +");
            ret.append(ignoreDAM);
            ret.append(" ");
        }
        if (ignoreDAMr > 0) {
            ret.append("无視伤害 : +");
            ret.append(ignoreDAMr);
            ret.append("% ");
        }
        if (incMHP > 0) {
            ret.append("MaxHp : +");
            ret.append(incMHP);
            ret.append(" ");
        }
        if (incMMP > 0) {
            ret.append("MaxMp : +");
            ret.append(incMMP);
            ret.append(" ");
        }
        if (incMHPr > 0) {
            ret.append("MaxHp : +");
            ret.append(incMHPr);
            ret.append("% ");
        }
        if (incMMPr > 0) {
            ret.append("MaxMp : +");
            ret.append(incMMPr);
            ret.append("% ");
        }
        if (incSTR > 0) {
            ret.append("力量 : +");
            ret.append(incSTR);
            ret.append(" ");
        }
        if (incDEX > 0) {
            ret.append("敏捷 : +");
            ret.append(incDEX);
            ret.append(" ");
        }
        if (incINT > 0) {
            ret.append("智力 : +");
            ret.append(incINT);
            ret.append(" ");
        }
        if (incLUK > 0) {
            ret.append("運氣 : +");
            ret.append(incLUK);
            ret.append(" ");
        }
        if (incACC > 0) {
            ret.append("命中值 : +");
            ret.append(incACC);
            ret.append(" ");
        }
        if (incEVA > 0) {
            ret.append("回避值 : +");
            ret.append(incEVA);
            ret.append(" ");
        }
        if (incSpeed > 0) {
            ret.append("移动速度 : +");
            ret.append(incSpeed);
            ret.append(" ");
        }
        if (incJump > 0) {
            ret.append("跳躍力 : +");
            ret.append(incJump);
            ret.append(" ");
        }
        if (incPAD > 0) {
            ret.append("攻击力 : +");
            ret.append(incPAD);
            ret.append(" ");
        }
        if (incMAD > 0) {
            ret.append("魔力 : +");
            ret.append(incMAD);
            ret.append(" ");
        }
        if (incPDD > 0) {
            ret.append("物理防御力 : +");
            ret.append(incPDD);
            ret.append(" ");
        }
        if (incMDD > 0) {
            ret.append("魔法防御力 : +");
            ret.append(incMDD);
            ret.append(" ");
        }
        if (incSTRr > 0) {
            ret.append("力量 : +");
            ret.append(incSTRr);
            ret.append("% ");
        }
        if (incDEXr > 0) {
            ret.append("敏捷 : +");
            ret.append(incDEXr);
            ret.append("% ");
        }
        if (incINTr > 0) {
            ret.append("智力 : +");
            ret.append(incINTr);
            ret.append("% ");
        }
        if (incLUKr > 0) {
            ret.append("运气 : +");
            ret.append(incLUKr);
            ret.append("% ");
        }
        if (incACCr > 0) {
            ret.append("命中值 : +");
            ret.append(incACCr);
            ret.append("% ");
        }
        if (incEVAr > 0) {
            ret.append("回避值 : +");
            ret.append(incEVAr);
            ret.append("% ");
        }
        if (incPADr > 0) {
            ret.append("攻击力 : +");
            ret.append(incPADr);
            ret.append("% ");
        }
        if (incMADr > 0) {
            ret.append("魔力 : +");
            ret.append(incMADr);
            ret.append("% ");
        }
        if (incPDDr > 0) {
            ret.append("物理防御力 : +");
            ret.append(incPDDr);
            ret.append("% ");
        }
        if (incMDDr > 0) {
            ret.append("魔法防御力 : +");
            ret.append(incMDDr);
            ret.append("% ");
        }
        if (incCr > 0) {
            ret.append("一击必杀概率 : +");
            ret.append(incCr);
            ret.append("% ");
        }
        if (incDAMr > 0) {
            ret.append("总伤害 : +");
            ret.append(incDAMr);
            ret.append("% ");
        }
        if (level > 0) {
            ret.append("等级 : ");
            ret.append(level);
            ret.append(" ");
        }
        return ret.toString();
    }
}
