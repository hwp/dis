package de.dis2013.core;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.dis2013.data.Haus;
import de.dis2013.data.Immobilie;
import de.dis2013.data.Kaufvertrag;
import de.dis2013.data.Makler;
import de.dis2013.data.Mietvertrag;
import de.dis2013.data.Person;
import de.dis2013.data.Wohnung;

/**
 * Klasse zur Verwaltung aller Datenbank-Entitäten.
 * 
 * TODO: Aktuell werden alle Daten im Speicher gehalten. Ziel der Übung ist es,
 * schrittweise die Datenverwaltung in die Datenbank auszulagern. Wenn die
 * Arbeit erledigt ist, werden alle Sets dieser Klasse überflüssig.
 */
public class ImmoService {
	// Hibernate Session
	private SessionFactory sessionFactory;

	public ImmoService() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	/**
	 * Finde einen Makler mit gegebener Id
	 * 
	 * @param id
	 *            Die ID des Maklers
	 * @return Makler mit der ID oder null
	 */
	public Makler getMaklerById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Makler r = (Makler) session.get(Makler.class, id);
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Finde einen Makler mit gegebenem Login
	 * 
	 * @param login
	 *            Der Login des Maklers
	 * @return Makler mit der ID oder null
	 */
	public Makler getMaklerByLogin(String login) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Makler r = (Makler) session
				.createQuery("from Makler as m where m.login = ?")
				.setString(0, login).uniqueResult();
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Gibt alle Makler zurück
	 */
	public Set<Makler> getAllMakler() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Set<Makler> r = new HashSet<Makler>(session.createQuery("from Makler")
				.list());
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Finde eine Person mit gegebener Id
	 * 
	 * @param id
	 *            Die ID der Person
	 * @return Person mit der ID oder null
	 */
	public Person getPersonById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Person r = (Person) session.get(Person.class, id);
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Fügt einen Makler hinzu
	 * 
	 * @param m
	 *            Der Makler
	 */
	public void addMakler(Makler m) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Löscht einen Makler
	 * 
	 * @param m
	 *            Der Makler
	 */
	public void deleteMakler(Makler m) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(m);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Fügt eine Person hinzu
	 * 
	 * @param p
	 *            Die Person
	 */
	public void addPerson(Person p) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Gibt alle Personen zurück
	 */
	public Set<Person> getAllPersons() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Set<Person> r = new HashSet<Person>(session.createQuery("from Person")
				.list());
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Löscht eine Person
	 * 
	 * @param p
	 *            Die Person
	 */
	public void deletePerson(Person p) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(p);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Fügt ein Haus hinzu
	 * 
	 * @param h
	 *            Das Haus
	 */
	public void addHaus(Haus h) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(h);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Gibt alle Häuser eines Maklers zurück
	 * 
	 * @param m
	 *            Der Makler
	 * @return Alle Häuser, die vom Makler verwaltet werden
	 */
	public Set<Haus> getAllHaeuserForMakler(Makler m) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Set<Haus> r = new HashSet<Haus>(
				session.createQuery(
						"select Haus from Haus as h where h.verwalter = ?")
						.setEntity(1, m).list());
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Findet ein Haus mit gegebener ID
	 * 
	 * @param m
	 *            Der Makler
	 * @return Das Haus oder null, falls nicht gefunden
	 */
	public Haus getHausById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Haus r = (Haus) session.get(Haus.class, id);
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Löscht ein Haus
	 * 
	 * @param p
	 *            Das Haus
	 */
	public void deleteHouse(Haus h) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(h);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Fügt eine Wohnung hinzu
	 * 
	 * @param w
	 *            die Wohnung
	 */
	public void addWohnung(Wohnung w) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(w);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Gibt alle Wohnungen eines Maklers zurück
	 * 
	 * @param m
	 *            Der Makler
	 * @return Alle Wohnungen, die vom Makler verwaltet werden
	 */
	public Set<Wohnung> getAllWohnungenForMakler(Makler m) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Set<Wohnung> r = new HashSet<Wohnung>(
				session.createQuery(
						"From Wohnung as w where w.verwalter = ?")
						.setEntity(1, m).list());
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Findet eine Wohnung mit gegebener ID
	 * 
	 * @param id
	 *            Die ID
	 * @return Die Wohnung oder null, falls nicht gefunden
	 */
	public Wohnung getWohnungById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Wohnung r = (Wohnung) session.get(Wohnung.class, id);
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Löscht eine Wohnung
	 * 
	 * @param p
	 *            Die Wohnung
	 */
	public void deleteWohnung(Wohnung w) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(w);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Fügt einen Mietvertrag hinzu
	 * 
	 * @param w
	 *            Der Mietvertrag
	 */
	public void addMietvertrag(Mietvertrag m) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Fügt einen Kaufvertrag hinzu
	 * 
	 * @param w
	 *            Der Kaufvertrag
	 */
	public void addKaufvertrag(Kaufvertrag k) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(k);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Gibt alle Mietverträge zu Wohnungen eines Maklers zurück
	 * 
	 * @param m
	 *            Der Makler
	 * @return Alle Mietverträge, die zu Wohnungen gehören, die vom Makler
	 *         verwaltet werden
	 */
	public Set<Mietvertrag> getAllMietvertraegeForMakler(Makler m) {
		return getMietvertragByVerwalter(m);
	}

	/**
	 * Gibt alle Kaufverträge zu Wohnungen eines Maklers zurück
	 * 
	 * @param m
	 *            Der Makler
	 * @return Alle Kaufverträge, die zu Häusern gehören, die vom Makler
	 *         verwaltet werden
	 */
	public Set<Kaufvertrag> getAllKaufvertraegeForMakler(Makler m) {
		return getKaufvertragByVerwalter(m);
	}

	/**
	 * Findet einen Mietvertrag mit gegebener ID
	 * 
	 * @param id
	 *            Die ID
	 * @return Der Mietvertrag oder null, falls nicht gefunden
	 */
	public Mietvertrag getMietvertragById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Mietvertrag r = (Mietvertrag) session.get(Mietvertrag.class, id);
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Findet alle Mietverträge, die Wohnungen eines gegebenen Verwalters
	 * betreffen
	 * 
	 * @param id
	 *            Der Verwalter
	 * @return Set aus Mietverträgen
	 */
	public Set<Mietvertrag> getMietvertragByVerwalter(Makler m) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Set<Mietvertrag> r = new HashSet<Mietvertrag>(
				session.createQuery(
						"select Mietvertrag from Mietvertrag as vetrag, Immobilie as immo where vertrag.wohnung = immo and immo.verwalter = ?")
						.setEntity(1, m).list());
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Findet alle Kaufverträge, die Häuser eines gegebenen Verwalters betreffen
	 * 
	 * @param id
	 *            Der Verwalter
	 * @return Set aus Kaufverträgen
	 */
	public Set<Kaufvertrag> getKaufvertragByVerwalter(Makler m) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Set<Kaufvertrag> r = new HashSet<Kaufvertrag>(
				session.createQuery(
				"select Kaufvertrag from Kaufvertrag as vetrag, Immobilie as immo where vertrag.haus = immo and immo.verwalter = ?")
				.setEntity(1, m).list());
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Findet einen Kaufvertrag mit gegebener ID
	 * 
	 * @param id
	 *            Die ID
	 * @return Der Kaufvertrag oder null, falls nicht gefunden
	 */
	public Kaufvertrag getKaufvertragById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Kaufvertrag r = (Kaufvertrag) session.get(Kaufvertrag.class, id);
		session.getTransaction().commit();
		session.close();
		return r;
	}

	/**
	 * Löscht einen Mietvertrag
	 * 
	 * @param m
	 *            Der Mietvertrag
	 */
	public void deleteMietvertrag(Mietvertrag m) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(m);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Fügt einige Testdaten hinzu
	 */
	public void addTestData() {
		Makler m = new Makler();
		m.setName("Max Mustermann");
		m.setAdresse("Am Informatikum 9");
		m.setLogin("max");
		m.setPasswort("max");
		this.addMakler(m);

		Person p1 = new Person();
		p1.setAdresse("Informatikum");
		p1.setNachname("Mustermann");
		p1.setVorname("Erika");

		Person p2 = new Person();
		p2.setAdresse("Reeperbahn 9");
		p2.setNachname("Albers");
		p2.setVorname("Hans");

		this.addPerson(p1);
		this.addPerson(p2);

		Haus h = new Haus();
		h.setOrt("Hamburg");
		h.setPlz(22527);
		h.setStrasse("Vogt-Kölln-Straße");
		h.setHausnummer("2a");
		h.setFlaeche(384);
		h.setStockwerke(5);
		h.setKaufpreis(10000000);
		h.setGarten(true);
		h.setVerwalter(m);
		this.addHaus(h);

		Wohnung w = new Wohnung();
		w.setOrt("Hamburg");
		w.setPlz(22527);
		w.setStrasse("Vogt-Kölln-Straße");
		w.setHausnummer("3");
		w.setFlaeche(120);
		w.setStockwerk(4);
		w.setMietpreis(790);
		w.setEbk(true);
		w.setBalkon(false);
		w.setVerwalter(m);
		this.addWohnung(w);

		w = new Wohnung();
		w.setOrt("Berlin");
		w.setPlz(22527);
		w.setStrasse("Vogt-Kölln-Straße");
		w.setHausnummer("3");
		w.setFlaeche(120);
		w.setStockwerk(4);
		w.setMietpreis(790);
		w.setEbk(true);
		w.setBalkon(false);
		w.setVerwalter(m);
		this.addWohnung(w);

		Session session = sessionFactory.openSession();
		// Hibernate Session erzeugen
		session = sessionFactory.openSession();
		session.beginTransaction();
		Makler m2 = (Makler) session.get(Makler.class, m.getId());

		Set<Immobilie> immos = m2.getImmobilien();
		Iterator<Immobilie> it = immos.iterator();

		while (it.hasNext()) {
			Immobilie i = it.next();
			System.out.println("Immo: " + i.getOrt());
		}
		session.close();

		session = sessionFactory.openSession();
		session.beginTransaction();
		// TODO : question (detatched -> transient)
		session.update(h);
		System.out.println(h.getId() + ", " + h.getStrasse());

		Kaufvertrag kv = new Kaufvertrag();
		kv.setHaus(h);
		kv.setVertragspartner(p1);
		kv.setVertragsnummer(9234);
		kv.setDatum(new Date(System.currentTimeMillis()));
		kv.setOrt("Hamburg");
		kv.setAnzahlRaten(5);
		kv.setRatenzins(4);
		session.save(kv);
		// this.addKaufvertrag(kv);
		session.getTransaction().commit();
		session.close();
		// TODO Strange ...

		Mietvertrag mv = new Mietvertrag();
		mv.setWohnung(w);
		mv.setVertragspartner(p2);
		mv.setVertragsnummer(23112);
		mv.setDatum(new Date(System.currentTimeMillis() - 1000000000));
		mv.setOrt("Berlin");
		mv.setMietbeginn(new Date(System.currentTimeMillis()));
		mv.setNebenkosten(65);
		mv.setDauer(36);
		this.addMietvertrag(mv);
	}
}
