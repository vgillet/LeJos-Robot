package metier;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import metier.Motor;
import ressources.Etat;

public class VehiculeControler {		
	
	Etat etatVehicule;
	
	private Motor moteurGauche; 
	private Motor moteurDroit;
    
	
	public VehiculeControler() {
		etatVehicule = Etat.contact;		
		this.moteurGauche = new Motor(new EV3LargeRegulatedMotor(MotorPort.D));
		this.moteurDroit = new Motor(new EV3LargeRegulatedMotor(MotorPort.A));
		RegulatedMotor T[] = {this.moteurDroit.getMotor()};
		this.moteurGauche.getMotor().synchronizeWith(T);		
	}
	
	int vitesseRange = 25;
	
	
	public void start(){	
	// certaines conditions sont int�gr�es dans des public voids
	// interm�diaires afin de ne pas polluer le code.
		if (etatVehicule == Etat.contact)
		{
			etatVehicule = Etat.neutral;				
		}
		else
		{
			System.out.println("Moteur d�j� d�marr�");
		}
	}
	
	public void stop(){
		if(etatVehicule == Etat.forward || etatVehicule == Etat.backward)
		{
			etatVehicule = Etat.neutral;
			moteurGauche.getMotor().startSynchronization();
			moteurGauche.stop();
			moteurDroit.stop();
	        moteurGauche.getMotor().endSynchronization();
	        
		}
		else
		{
			System.out.println("vehicule d�j� � l'arret");
		}
	}
	
	public void forward(){
	// if { le moteur est en marche arri�re il doit d�abord passer
	// par l��tat neutral
	// (point mort) pour pouvoir �tre en marche avant (forward)
		if(etatVehicule == Etat.neutral)
		{
			etatVehicule = Etat.forward;
			moteurGauche.getMotor().startSynchronization();
			moteurGauche.forward();
			moteurDroit.forward();
			moteurGauche.getMotor().endSynchronization();
	        
		}
		else
		{
			System.out.println("Impossible de faire avancer");
		}
	}
	
	public void backward()
	{
		if (etatVehicule == Etat.neutral)
		{
			etatVehicule = Etat.backward;
			moteurGauche.getMotor().startSynchronization();
			moteurGauche.backward();
			moteurDroit.backward();
			moteurGauche.getMotor().endSynchronization();
		}
		else
		{	
			System.out.println("Impossible de faire reculer");
		}
	}
	
	public void left(){
		if (etatVehicule == Etat.forward || etatVehicule == Etat.backward)
		{
			moteurGauche.getMotor().startSynchronization();
			moteurGauche.setSpeed(moteurGauche.getSpeed());
			moteurDroit.setSpeed(moteurDroit.getSpeed() * 2);
			moteurGauche.getMotor().endSynchronization();			
		}
		else
		{
			System.out.println("Impossible de faire tourner � gauche");
		}
	}
	
	public void right()
	{
		if (etatVehicule == Etat.forward || etatVehicule == Etat.backward)
		{
			moteurGauche.getMotor().startSynchronization();
			moteurGauche.setSpeed(moteurGauche.getSpeed()* 2);
			moteurDroit.setSpeed(moteurDroit.getSpeed());
			moteurGauche.getMotor().endSynchronization();
		}
		else
		{
			System.out.println("Impossible de faire tourner � droite");
		}
	}
	
	/*public void ring()
	{
		buzz();
		logger.info("left() : klaxon");
	}*/
	
	public void up()
	{
		if (moteurGauche.getSpeed() < 250 && moteurDroit.getSpeed() < 250 ) {
			moteurGauche.getMotor().startSynchronization();
			moteurGauche.setSpeed(moteurGauche.getSpeed() + vitesseRange);
			moteurDroit.setSpeed(moteurDroit.getSpeed() + vitesseRange);
			moteurGauche.getMotor().endSynchronization();
		 }
	}
	
	
	public void down()
	{
		if (moteurGauche.getSpeed() > vitesseRange && moteurDroit.getSpeed() > vitesseRange ) {
			moteurGauche.getMotor().startSynchronization();
			moteurGauche.setSpeed(moteurGauche.getSpeed() - vitesseRange);
			moteurDroit.setSpeed(moteurDroit.getSpeed() - vitesseRange);
			moteurGauche.getMotor().endSynchronization();
		 }
		else
		{
			stop();
		}
	}
	/*
	public void urgency()
	{
		moteur.urgency();
		logger.info("urgency() : le v�hicule se bloque");
	}
	
	public void breakdown()
	{
		moteur.breakdown();
		logger.info("breakdown() : simulation de panne");
	}
	
	public void restore()
	{
		moteur.restoreMotor();	
		logger.info("restore() : le v�hicule restaure son �tat");
	}
	/*public void contact()
	{
		logger.info("contact() : le v�hicule a eu un contact");
		return capteurContact.contact();
	}
	
	public void obstacleDetection()
	{
		logger.info("obstacleDetection() : le v�hicule a observ� un obstacle");
		return capteurUltrason.detect();
	}
	
	public void brightness()
	{
		logger.info("brightness() : le v�hicule a rep�r� une couleur");
		return capteurCouleur.color();
	}*/
	
	/*public void rotationAngle()
	{
		logger.info("rotationAngle() : r�cup�ration de l�angle du capteur gyroscopique");
		return capteurGyro.directionAngle();
	}*/
	
	/*public void EtatRobot()
	{
		int vitesse;
		int angle;
		Etat etatMoteur;
		vitesse = moteur.getVitesse();
		angle = moteur.getAngle();
		etatMoteur = moteur.getEtatMoteur();
		logger.info("getEtatRobot() : r�cup�ration de l��tat du robot");
		//retourner l��tat du robot au format json
	}*/
	
	/*public void request(Chaine de caract�re message)
	{
		if(message = �getEtatRobot�)
		{
			envoyer une requ�te avec l��tat du robot getEtatRobot()
		}
		else if 
		{
		...
		}
		logger.info("request() : envoi d�une requ�te " + message +");
	}
	
	// public void utile � cette classe
	public void motorIsStart()
	{
		return moteur.getEtatMoteur();
	}
	*/
	
	public Etat getEtatVehicule() {
		return etatVehicule;
	}

	public void setEtatVehicule(Etat etatVehicule) {
		this.etatVehicule = etatVehicule;
	}

}
