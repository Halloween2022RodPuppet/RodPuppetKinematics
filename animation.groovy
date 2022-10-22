import com.neuronrobotics.bowlerstudio.BowlerStudio

import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl

def path = ScriptingEngine
	.fileFromGit(
		"https://github.com/Halloween2022RodPuppet/RodPuppetKinematics.git",//git repo URL
		null,//branch
		"song.wav"// File from within the Git repo
	)
println path

try
{
	AudioInputStream audioStream = AudioSystem.getAudioInputStream(path)
	Clip audioClip = AudioSystem.getClip();
	audioClip.open(audioStream);
	FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
	//float gainValue = (((float) config.volume()) * 40f / 100f) - 35f;
	//gainControl.setValue(gainValue);

	audioClip.start();
	ThreadUtil.wait(1);
	try{
		while(audioClip.isRunning()&& !Thread.interrupted()){
			double pos =(double) audioClip.getMicrosecondPosition()/1000.0
			double len =(double) audioClip.getMicrosecondLength()/1000.0
			def percent = pos/len*100.0
			System.out.println("Current "+pos +" Percent = "+percent);
			ThreadUtil.wait(10);
		}
	}catch(Throwable t){
		BowlerStudio.printStackTrace(t)
	}
	audioClip.stop()
	audioClip.close()
	((AudioInputStream)audioStream).close()
	
}catch(java.lang.InterruptedException ex) {
//exit sig	
}catch (Exception e)
{
	BowlerStudio.printStackTrace(e)
	return null;
}

