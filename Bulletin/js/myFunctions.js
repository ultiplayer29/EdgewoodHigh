function getModTimes(schedule){
	var modTime = [new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date()];
	var modLength;
	var homeroomLength;
	modTime[0].setHours(7);
	modTime[0].setMinutes(50);
	modTime[0].setSeconds(0);
	
	if(schedule.indexOf("LITURGY")>-1){
		modLength = 42*60*1000;
		homeroomLength = 77*60*1000;
		modTime[1].setTime(modTime[0].getTime()+modLength);
		modTime[2].setTime(modTime[1].getTime()+modLength);
		modTime[3].setTime(modTime[2].getTime()+homeroomLength);
		for(x=4;x<modTime.length;x++){
			modTime[x].setTime(modTime[x-1].getTime() + modLength);
		}
	}
	else{
		if(schedule.indexOf("REGULAR")>-1){
			modLength = 49 * 60 * 1000;
			homeroomLength = 14 * 60 * 1000;
		}
		else if(schedule.indexOf("EARLY")>-1){
			modLength = 44 * 60 *1000;
			homeroomLength = 14 * 60 *1000;
		}
		else if(schedule.indexOf("DEVELOPMENT")){
			modLength = 40*60*1000;
			homeroomLength = 10*60*1000;
		}
		modTime[1].setTime(modTime[0].getTime() + modLength);
		modTime[2].setTime(modTime[1].getTime() + homeroomLength);
		for(x=3;x<modTime.length;x++){
			modTime[x].setTime(modTime[x-1].getTime() + modLength);
		}
	}
	return modTime;
	
}
function simpleTitle(title){
	var newTitle = title;
	if(newTitle.indexOf("ON A")>-1){
		newTitle = newTitle.replace("ON A", "");		
	}
	if(newTitle.indexOf("PROFESSIONAL")>-1){
		newTitle = newTitle.replace("PROFESSIONAL DEVELOPMENT","PROFESSIONAL DEV");
	}
	return newTitle;
}

function verticalScroll(parent, child, time){
	
	$(parent).animate({ scrollTop: $(child).height()-$(window).height() }, time, "linear");
}
function getCurrentTime(){
	var now = new Date();
	var minutes = now.getMinutes();
	var hours = now.getHours();
	var ampm = "am";
	if(hours>=12){
		ampm="pm";
	}
	else{
		ampm="am";
	}
	if(hours >12){
		hours = hours-12;
		
	}
	if(minutes<10){
		minutes = "0"+minutes;
	}
	var currentTime = hours+":"+minutes+" "+ampm;
	
	return currentTime;
}