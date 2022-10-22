
import com.neuronrobotics.sdk.addons.gamepad.BowlerJInputDevice
import com.neuronrobotics.sdk.addons.gamepad.IGameControlEvent
import com.neuronrobotics.sdk.addons.kinematics.MobileBase
import com.neuronrobotics.sdk.common.DeviceManager
import com.neuronrobotics.bowlerstudio.assets.ConfigurationDatabase
import com.neuronrobotics.bowlerstudio.creature.MobileBaseLoader

MobileBase base=DeviceManager.getSpecificDevice( "JackSkellington",{
	//If the device does not exist, prompt for the connection

	MobileBase m = MobileBaseLoader.fromGit(
			"https://github.com/Halloween2022RodPuppet/RodPuppetKinematics.git",
			"rodpuppet.xml"
			)
	return m
})

println base.getScriptingName()

List<String> gameControllerNames = ConfigurationDatabase.getObject("katapult", "gameControllerNames", [
	"Dragon",
	"X-Box",
	"Game",
	"Switch"
])

//Check if the device already exists in the device Manager
BowlerJInputDevice g=DeviceManager.getSpecificDevice("gamepad",{
	def t = new BowlerJInputDevice(gameControllerNames); //
	t.connect(); // Connect to it.
	return t
})

if (g==null)
	return

float x =0;

float straif=0;
float rz=0;
float ljud =0;
float trigButton=0;
float trigAnalog=0;
float tilt=0;
long timeOfLastCommand = System.currentTimeMillis()
IGameControlEvent listener = new IGameControlEvent() {
			@Override public void onEvent(String name,float value) {
				timeOfLastCommand = System.currentTimeMillis()
				if(name.contentEquals("l-joy-left-right")){
					straif=value;
				}
				else if(name.contentEquals("r-joy-up-down")){
					x=-value;
				}
				else if(name.contentEquals("l-joy-up-down")){
					ljud=value;
				}
				else if(name.contentEquals("r-joy-left-right")){
					rz=value;
				}else if(name.contentEquals("analog-trig")){
					trigAnalog=value/2.0+0.5;
				}else if(name.contentEquals("z")){
					trigButton=value/2.0+0.5;
				}
				else if(name.contentEquals("x-mode")){
					if(value>0) {

					}
				}else if(name.contentEquals("r-trig-button")){
					if(value>0) {
						tilt=1;
					}else
						tilt=0;
				}
				else if(name.contentEquals("l-trig-button")){
					if(value>0) {
						tilt=-1;
					}else
						tilt=0;
				}
				else if(name.contentEquals("y-mode")){
					if(value>0) {

					}
				}
				//System.out.println(name+" is value= "+value);

			}
		}

g.clearListeners()
Log.enableSystemPrint(true)
g.addListeners(listener);
try{
	def lasttrig=0;
	while(!Thread.interrupted() ){
		Thread.sleep(30)

	}
}catch(java.lang.InterruptedException ex) {
//exit sig	
}catch(Throwable t){
	t.printStackTrace(System.out)
}
g.removeListeners(listener);
