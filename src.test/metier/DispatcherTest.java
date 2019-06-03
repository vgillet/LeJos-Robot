package metier;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ressources.Etat;

public class DispatcherTest {

	private VehiculeControler vehicule;

	@Before
	public void init() {
		vehicule = new VehiculeControler();
	}

	@Test
	public void testOn() {

		VehiculeControler vehicule = new VehiculeControler();

		assertEquals(Etat.off, vehicule.getEtatVehicule());

		vehicule.start();
		assertEquals(Etat.neutral, vehicule.getEtatVehicule());
	}

	@Test
	public void testStop() {
		assertEquals(Etat.neutral, vehicule.getEtatVehicule());

		vehicule.stop();
		assertEquals(Etat.contact, vehicule.getEtatVehicule());

	}

	@Test
	public void testStopForward() {

		vehicule.start();
		vehicule.forward();
		vehicule.stop();
		assertEquals(Etat.neutral, vehicule.getEtatVehicule());
	}

	@Test
	public void testStopBackward() {

		vehicule.start();
		vehicule.backward();
		vehicule.stop();
		assertEquals(Etat.neutral, vehicule.getEtatVehicule());
	}

	@Test
	public void testForward() {
		assertEquals(Etat.contact, vehicule.getEtatVehicule());

		vehicule.forward();
		assertEquals(Etat.contact, vehicule.getEtatVehicule());

		vehicule.start();
		vehicule.forward();
		assertEquals(Etat.forward, vehicule.getEtatVehicule());
		assertEquals(25, vehicule.getMoteurGauche().getVitesse());
		assertEquals(25, vehicule.getMoteurDroit().getVitesse());

	}

	@Test
	public void testBackward() {

		assertEquals(Etat.contact, vehicule.getEtatVehicule());

		vehicule.backward();
		assertEquals(Etat.contact, vehicule.getEtatVehicule());

		vehicule.start();
		vehicule.backward();
		assertEquals(Etat.backward, vehicule.getEtatVehicule());
		assertEquals(25, vehicule.getMoteurGauche().getVitesse());
		assertEquals(25, vehicule.getMoteurDroit().getVitesse());
	}

	@Test
	public void testLeft() {

		int vitesse;

		vehicule.start();
		vehicule.forward();
		vehicule.left();

		vitesse = (int) ((int) vehicule.saveVitesseMoteurGauche * 0.66);
		assertEquals(vitesse, vehicule.getMoteurGauche().getVitesse());

		vitesse = (int) ((int) vehicule.saveVitesseMoteurDroit * 1.33);
		assertEquals(vitesse, vehicule.getMoteurDroit().getVitesse());
	}

	@Test
	public void testRight() {
		int vitesse;

		vehicule.start();
		vehicule.forward();
		vehicule.left();

		vitesse = (int) ((int) vehicule.saveVitesseMoteurGauche * 1.33);
		assertEquals(vitesse, vehicule.getMoteurGauche().getVitesse());

		vitesse = (int) ((int) vehicule.saveVitesseMoteurDroit * 0.66);
		assertEquals(vitesse, vehicule.getMoteurDroit().getVitesse());
	}

	@Test
	public void testUp() {
		vehicule.start();
		vehicule.forward();
		vehicule.up();

		assertEquals(vehicule.getVitesseRange() + vehicule.getVitesseRange(), vehicule.getMoteurDroit().getVitesse());
		assertEquals(vehicule.getVitesseRange() + vehicule.getVitesseRange(), vehicule.getMoteurGauche().getVitesse());
	}

	@Test
	public void testDown() {
		vehicule.start();
		vehicule.forward();
		vehicule.down();

		assertEquals(vehicule.getVitesseRange() - vehicule.getVitesseRange(), vehicule.getMoteurDroit().getVitesse());
		assertEquals(vehicule.getVitesseRange() - vehicule.getVitesseRange(), vehicule.getMoteurGauche().getVitesse());

		vehicule.down();
		assertEquals(0, vehicule.getMoteurDroit().getVitesse());
		assertEquals(0, vehicule.getMoteurGauche().getVitesse());
		assertEquals(Etat.neutral, vehicule.getEtatVehicule());
	}

	@Test
	public void testUrgency() {
		vehicule.start();
		vehicule.forward();
		vehicule.urgency();

		assertEquals(0, vehicule.getMoteurDroit().getVitesse());
		assertEquals(0, vehicule.getMoteurGauche().getVitesse());
		assertEquals(Etat.urgency, vehicule.getEtatVehicule());
	}

	@Test
	public void testBreakdown() {
		vehicule.start();
		vehicule.forward();
		vehicule.breakdown();

		assertEquals(0, vehicule.getMoteurDroit().getVitesse());
		assertEquals(0, vehicule.getMoteurGauche().getVitesse());
		assertEquals(Etat.panne, vehicule.getEtatVehicule());
	}

}
