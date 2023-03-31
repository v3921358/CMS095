var status = 0;
var name;

function start()
{
 status = -1;
 action(1,0,0);
 }
 
 function action(mode, type, selection)
 {
	if (mode == -1)
	{cm.dispose();
	}
	else
	{
		if(status >= 2 && mode == 0)
			{cm.sendOk("Have fun Shithead!");
			cm.dispose();
			return;
			}
		if(mode ==1)
			{status++;
			}
		else{status--;}
		if(status == 0)
			{
			cm.sendNext("I sell soda, what's it to you?");
			}
		else {
				if(status == 1)
				{
					cm.sendYesNo("Do you want a soda?");
				}
				else 
				{if(status == 2)
				{
				cm.sendOk("Have fun Shithead!");
				cm.gainItem(3994431,1);
				cm.dispose();
				}
				else{cm.dispose();}
				}
				}
	}
}