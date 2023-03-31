var possibleJobs = new Array();

function start() {
    if (cm.getPlayer().getJob() == 0 || cm.getPlayer().getJob() == 1000 || cm.getPlayer().getJob() == 2000 || cm.getPlayer().getJob() ==  3000) {
        var colorBoolInverter = true;
        for (var i = 1; i < 6; i++) 
            possibleJobs.push(0 + 100 * i);
        possibleJobs.push(2400);
        possibleJobs.push(430);
        possibleJobs.push(508);
        possibleJobs.push(501);
        for (var i = 1; i < 6; i++) 
            possibleJobs.push(1000 + 100 * i);
        for (var i = 1; i < 4; i++)
            possibleJobs.push(2000 + 100 * i);
        for (var i = 1; i < 4; i++)
            possibleJobs.push(3000 + 100 * i);
        possibleJobs.push(3500);
    } else if (cm.getPlayer().getLevel() > 69 && cm.isValidJob(cm.getPlayer().getJob() + 2)) {
        possibleJobs.push(cm.getPlayer().getJob() + 1);
    } else if (cm.getPlayer().getLevel() > 119 && cm.isValidJob(cm.getPlayer().getJob() + 1)) {
        possibleJobs.push(cm.getPlayer().getJob() + 1);
    } else if (cm.getPlayer().getLevel() > 29) {
        switch (cm.getPlayer().getJob()) {
            case 100:
            case 200:
                possibleJobs.push(cm.getPlayer().getJob() + 10);
                possibleJobs.push(cm.getPlayer().getJob() + 20);
                possibleJobs.push(cm.getPlayer().getJob() + 30);
                  break;
            case 300:
            case 400:
                possibleJobs.push(cm.getPlayer().getJob() + 10);
                  possibleJobs.push(cm.getPlayer().getJob() + 20);
                  break;
            case 430:
                   possibleJobs.push(cm.getPlayer().getJob() + 2);
                  break;
            case 500:
                  possibleJobs.push(cm.getPlayer().getJob() + 10);
                possibleJobs.push(cm.getPlayer().getJob() + 20);
                break;
            case 501:
                possibleJobs.push(cm.getPlayer().getJob() + 30);
                break;
            case 508:
	possibleJobs.push(cm.getPlayer().getJob()+ 62);
	break;
            case 1100:
            case 1200:
            case 1300:
            case 1400:
            case 1500:
            case 2100:
            case 2200:
            case 2300:
            case 2400:
            case 3100:
            case 3200:
            case 3300:
            case 3500:
                 possibleJobs.push(cm.getPlayer().getJob() + 10);
                  break;
            default:
                break;
        }
    }
    if (possibleJobs.length == 0) {
        cm.sendOk("There is no available jobs for you to take at this point.");
        cm.dispose();
    } else {
        var text = "Available Jobs:#b";
        for (var j = 0; j < possibleJobs.length; j++) 
            text += "\r\n#L"+j+"#"+cm.getJobName(possibleJobs[j])+"#l";
        cm.sendSimple(text);
    }
}

function action(m,t,s) {
    if (m < 1) {
        cm.dispose();
    } else {
        cm.changeJob(possibleJobs[s]);
        cm.dispose();
    }
}  