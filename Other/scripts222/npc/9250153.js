/*
    This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
                       Matthias Butz <matze@odinms.de>
                       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/* JukeBox NPC
   Computer - 1052013
*/

var status = 0;
var price = 5000000;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendSimple("Hi~I can play some songs.But You Must Insert 5Mil To Play.#b\r\n#L0#FloralLife#l\r\n#L1#BadGuys#l\r\n#L2#Nightmare#l\r\n#L3#MissingYou#l\r\n#L4#PlayWithMe#l\r\n#L5#WhiteChristmas#l\r\n#L6#UponTheSky#l\r\n#L7#Shinin'Harbor#l\r\n#L8#Ariant#l\r\n#L9#ComeWithMe#l\r\n#L10#Fantasia#l\r\n#L11#Aquarium#l\r\n#L12#CokeTown#l\r\n#L13#Leafre#l\r\n#L14#Amoria#l\r\n#L15#Chapel#l\r\n#L16#FirstStepMaster#l\r\n#L17#Market(Henesys)#l\r\n#L18#WolfWood#l\r\n#L19#WelcomeToHell#l\r\n#L20#PlotOfPixie#l\r\n#L21#Horntail#l\r\n#L22#FunnyRabit#l\r\n#L23#Cathedral#l\r\n#L24#DragonLoad#l\r\n#L25#DragonNest#l\r\n#L26#TowerOfGoddess#l\r\n#L26#Eregos#l");
            } else if (status == 1) {
            if (selection == 0) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
            cm.sendOk("Enjoy~")
            cm.gainMeso(-price);
                  cm.Changemusic("Bgm00/FloralLife");
                  cm.dispose();
        }
            } else if (selection == 1) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm01/BadGuys");
                    cm.dispose();
        }
            } else if (selection == 2) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm00/Nightmare");
                    cm.dispose();
        }
            } else if (selection == 3) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm02/MissingYou");
                    cm.dispose();
        }
            } else if (selection == 4) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm04/PlayWithMe");
                    cm.dispose();
        }
            } else if (selection == 5) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm04/WhiteChristmas");
                    cm.dispose();
        }
            } else if (selection == 6) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm04/UponTheSky");
                    cm.dispose();
        }
            } else if (selection == 7) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm04/Shinin'Harbor");
                    cm.dispose();
        }
            } else if (selection == 8) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm14/Ariant");
                    cm.dispose();
        }
            } else if (selection == 9) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm06/ComeWithMe");
                    cm.dispose();
        }
            } else if (selection == 10) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm07/Fantasia");
                    cm.dispose();
        }
            } else if (selection == 11) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm11/Aquarium");
                    cm.dispose();
        }
            } else if (selection == 12) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm13/CokeTown");
                    cm.dispose();
        }
            } else if (selection == 13) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm13/Leafre");
                    cm.dispose();
        }
            } else if (selection == 14) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("BgmGL/amoria");
                    cm.dispose();
        }
            } else if (selection == 15) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("BgmGL/chapel");
                    cm.dispose();
        }
            } else if (selection == 16) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("BgmJp/FirstStepMaster");
                    cm.dispose();
        }
            } else if (selection == 17) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm00/GoPicnic");
                    cm.dispose();
        }
            } else if (selection == 18) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm05/WolfWood");
                    cm.dispose();
        }
            } else if (selection == 19) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~") 
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm06/WelcomeToTheHell");
                    cm.dispose();
        }
            } else if (selection == 20) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm08/PlotOfPixie");
                    cm.dispose();
        }
            } else if (selection == 21) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm14/HonTale");
                    cm.dispose();
        }
            } else if (selection == 22) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("BgmEvent/FunnyRabbit");
                    cm.dispose();
        }
            } else if (selection == 23) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("BgmGL/cathedral");
                    cm.dispose();
        }
            } else if (selection == 24) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm14/DragonLoad");
                    cm.dispose();
        }
            } else if (selection == 25) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm14/DragonNest");
                    cm.dispose();
        }
            } else if (selection == 26) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm13/TowerOfGoddess");
                    cm.dispose();
        }
            } else if (selection == 27) {
        if (cm.getMeso() < price) {
            cm.sendOk("Sorry You Dun Have Enough Money To Operate The Music.")
            cm.dispose();
        } else {
              cm.sendOk("Enjoy~")
              cm.gainMeso(-price);
                    cm.Changemusic("Bgm10/Eregos");
                    cm.dispose();
        }
              }
        }
    }
}