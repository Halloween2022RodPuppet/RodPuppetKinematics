
import com.neuronrobotics.bowlerstudio.scripting.ScriptingEngine
import com.neuronrobotics.sdk.addons.kinematics.DHParameterKinematics
import com.neuronrobotics.sdk.addons.kinematics.MobileBase
import com.neuronrobotics.sdk.addons.kinematics.math.RotationNR
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR
import com.neuronrobotics.sdk.addons.kinematics.parallel.ParallelGroup
import com.neuronrobotics.sdk.common.DeviceManager

MobileBase base=DeviceManager.getSpecificDevice( "Fanuc_Delta_DR",{
	return ScriptingEngine.gitScriptRun(	"https://github.com/Halloween2022RodPuppet/RodPuppetKinematics.git",
									"rodpuppet.xml",
									null
	  )
})

double baseCircleDiam = 50
double powerLinkLen = 100
double passiveLinkLen = 100
double eoaPlatRad =20
double passiveSepDist =0
DHParameterKinematics tar=null

for(DHParameterKinematics k:base.getAllDHChains()) {
	String name = k.getScriptingName()
	double yOffset = passiveSepDist/2
	double rot =0
	if(name.endsWith("CoreSpherical")) {
		tar=k
		continue;
	}
	if(name.contains("2")) {
		rot=120
	}
	if(name.contains("3")) {
		rot=240
	}
	def rotation = new TransformNR(0,0,0,new RotationNR(0, rot, 0))
	TransformNR limbRoot = rotation
		.times(new TransformNR(baseCircleDiam/2,yOffset,0,new RotationNR(0, 0,0)))
	limbRoot.setRotation(new RotationNR(0,rot-90,-89.9999))
	
	k.setDH_D(1, powerLinkLen)
	k.setDH_D(3, passiveLinkLen)
	ParallelGroup baseGetParallelGroup = base.getParallelGroup(k)

	if(baseGetParallelGroup!=null && !name.endsWith("1")) {
		double centerx = eoaPlatRad *Math.cos(Math.toRadians(rot))
		double centery =eoaPlatRad*Math.sin(Math.toRadians(rot))
		println "Rot "+rot+" x"+centerx+" y"+centery
		TransformNR	tipLoc = new TransformNR(-centerx,0,centery,new RotationNR(0, 0,0))
		println tipLoc
		baseGetParallelGroup.setTipOffset(k, tipLoc)
	}

	println limbRoot
	k.setRobotToFiducialTransform(limbRoot)
}

base.getParallelGroup(tar).setDesiredTaskSpaceTransform(tar.calcHome(), 0)
