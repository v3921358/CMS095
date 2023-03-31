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
				if(cm.getMeso() < 5000)
				{
					cm.sendOk("Here's some change and a hat shithead!");
					cm.gainMeso(5000);
					cm.gainItem(1002419,1);
					cm.dispose();
				}
				else
				{
					cm.sendOk("Get your own money shithead!");
					cm.dispose();
				}
			}