import com.neuronrobotics.sdk.addons.kinematics.DHChain
import com.neuronrobotics.sdk.addons.kinematics.DHLink
import com.neuronrobotics.sdk.addons.kinematics.DhInverseSolver
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR

import eu.mihosoft.vrl.v3d.Transform

return new DhInverseSolver() {

	@Override
	public double[] inverseKinematics(TransformNR target, double[] jointSpaceVector, DHChain chain) {
		
		ArrayList<DHLink> links = chain.getLinks();
		int linkNum = jointSpaceVector.length;
		double z = target.getZ();
		double y = target.getY();
		double x = target.getX();
		double a1 = Math.atan2(y , x);
		double a1d = Math.toDegrees(a1);
		
		def newTip = new Transform()
		.movex(x)
		.movey(y)
		.movez(z)
		.rotz(a1d)
		
		x=newTip.getX()
		y=newTip.getY()
		z=newTip.getZ()
		
		
		double a2 = Math.atan2(z,x); // Z angle using x axis and z axis
		double a2d = Math.toDegrees(a2);
		double wristVect =  Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)); // x and z vector
		double ext = wristVect -links.get(3).getD()
		
		jointSpaceVector[0]=a1d+180
		if(jointSpaceVector[0]>180)
			jointSpaceVector[0]=jointSpaceVector[0]-360
		jointSpaceVector[1]=a2d
		jointSpaceVector[2]=0
		jointSpaceVector[3]=ext		
		return jointSpaceVector;
	}
	
}