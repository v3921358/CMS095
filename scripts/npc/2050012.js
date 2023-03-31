randomStrings = 
["You can use @commands to get a list of all our commands.",
 "You can use @reborn help to see a list of all the reborn commands you may use.",
 "You may Wence, the NPC right next to me to job advance.",
 "@buycoco will exchange 2.1b mesos for a coconut which is worth 2.1b mesos. @sellcoco alternatively will exchange a coconut in your enventory for 2.1b mesos.",
 "The credit union NPC will trade vote points that you've earned from voting on the site (www.tropikms.info/?page=vote) for various useful stuff",
 "If you explore henesys further, you will find more npcs!",
 "The phantom job is actually in the game file maplestory v1.11 right now!",
 "The majority of maplestory private servers are developped in java while nexon make their clients and server in C++.",
 "TropikMS has been throught multiple versions: v83, v90, v97 and now, v1.11.",
 "If you're lucky, a mob might just drop a miracle fragment upon death which you can turn into a miracle cube if you got enough and double click them.",
 "If you vote everyday, you will receive a vote point for each votes which you can repeat every 12 hours. The vote point exchanger npc is somewhere in henesys, find it! (Credit Union)",
 "If you get banned here, you will also receive a ban on GameKiller. (The forum)",
 "Nick is really lazy when it comes to scripting npcs requiring him to get alot of IDs. (item ids for example)",
 "I ate subway as i was scripting this npc. (SHAMELESS ADVERTISEMENT)", 
 "Subway is alot better than McDonald or KFC.",
 "Dubstep is the best.",
 "LMFAO is too mainstream. Their songs suck, i don't see what people like in them. (Especially sexy and i know it. that song is a straight up piece of garbage)",
 "i ran out of ideas for this npc fk"]

colors = ["#r", "#b", "#g", "#d"];


function start() {
	cm.sendOk("Did you know ? \r\n" + colors[cm.getDoubleFloor(cm.getDoubleRandom() * colors.length)] + randomStrings[cm.getDoubleFloor(cm.getDoubleRandom() * randomStrings.length)]);
}

function action(m,t,s) {
	cm.dispose();
}