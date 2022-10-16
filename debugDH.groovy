import com.neuronrobotics.sdk.addons.kinematics.DHParameterKinematics
import com.neuronrobotics.sdk.addons.kinematics.MobileBase
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR
import com.neuronrobotics.sdk.common.DeviceManager

//Your code here

MobileBase mb = DeviceManager.getSpecificDevice("JackSkelington"); 

DHParameterKinematics kn = mb.getAllDHChains().get(0);

def joints = [30,30,0,20] as double[]

TransformNR tf =kn.forwardOffset(kn.forwardKinematics(joints))

println tf

def got = kn.inverseKinematics(kn.inverseOffset(tf))

println joints
println got