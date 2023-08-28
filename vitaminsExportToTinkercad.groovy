// code here
CSG servo = Vitamins.get("hobbyServo", "mg92b")
		.roty(-90)
		.toZMin()

CSG horn = Vitamins.get("hobbyServoHorn", "standardMicro1")		


return [servo,horn]


