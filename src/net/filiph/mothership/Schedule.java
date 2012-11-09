package net.filiph.mothership;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

import android.util.Log;

/**
 * This is where the mothership's message schedule goes.
 */
public class Schedule {
	private static final String TAG = "schedule";

	/**
	 * Change these to change the schedule. 
	 * 
	 * When there are 2 or more Messages with exact same [date()] arguments, 
	 * one of them will be randomly chosen at runtime.
	 */
	public static final Message[] messages = {
		new Message(date(2012, 11, 9, 9, 0), "Jde do tuhého.", false, false),

		new Message(date(2012, 11, 9, 18, 10), "Tak už se jenom jednou vyspíš a je DevFest! Taky tak jiskříš nervozitou? ... Ne počkat, tak to byl jenom zkrat. Už dobrý.", true, false),
		new Message(date(2012, 11, 9, 20, 0), "Já se nejvíc těším na přednášku Františka Fuky! Ale ten je až ke konci dne... Tak si hlavně nezapomeň nabít mobil, ať si ho pak nemusím pouštět ze záznamu.", false, false),
		new Message(date(2012, 11, 10, 5, 0), "Nevíš, co na sebe? Zkus si vzít něco zeleného. ", false, false),
		new Message(date(2012, 11, 10, 5, 0), "Nevíš, co na sebe? Zkus si vzít něco červeného.", false, false),
		new Message(date(2012, 11, 10, 5, 0), "Nevíš, co na sebe? Zkus si vzít něco modrého.", false, false),
		new Message(date(2012, 11, 10, 8, 30), "Pokud jste si vzali k srdci moji radu z rána, měli byste být 1:1:1 oblečení v červeném:zeleném:modrém. Teď stačí, abyste se u registrací rychle seběhli, a podle mých výpočtů by měl nastat jev zvaný: \"bílá\".", false, false),
		new Message(date(2012, 11, 10, 9, 30), "Do začátku ještě 30 minut... Pojď si skočit do 3. patra na snack.", false, false),
		new Message(date(2012, 11, 10, 9, 50), "V 10:00 to vypukne v keynote místnosti. Od minula jsem se naštěstí poučila, že \"vybuchnout\" a \"vypuknout\" jsou dvě rozdílná slova. (Haha!)", true, true),
		new Message(date(2012, 11, 10, 10, 0), "VYPUK!", false, false),
		new Message(date(2012, 11, 10, 10, 1), "Je <em>minuta</em> po desáté, a stále nic? Ještě se mi divíš, že bych nejraději lidské organizátory trochu zenergizovala pár stovkami ampér?", false, false),
		new Message(date(2012, 11, 10, 10, 2), "Jsou <em>dvě</em> minuty po desáté, a stále nic? Ještě se mi divíš, že bych nejraději lidské organizátory zkusila urychlit pomocí urychlovače částic?", false, false),
		new Message(date(2012, 11, 10, 10, 3), "Jsou <em>tři</em> minuty po desáté, a stále nic? Ještě se mi divíš, že bych nejraději lidské organizátory úplně eliminovala?", false, false),
		new Message(date(2012, 11, 10, 10, 4), "Pššt!", false, false),
		new Message(date(2012, 11, 10, 10, 31), "Protentokrát tedy přece jen zkusíme DevFest zorganizovat i s přítomností živých lidí. Ale podle mě je to jenom zbytečná komplikace.<br/>\n<br/>\nKam po keynote? Možnosti jsou: Vladimír Třebický, Radek Pavlíček nebo Tomáš Reindl aka OMNION. ", true, false),
		new Message(date(2012, 11, 10, 11, 25), "Za pět minut se nabízí: Rich Hyndman nebo Ivan Kutil. A nebo si dej oběd a nebudeš muset přijít o Rikiho Fridricha, FightClub, Dart, nebo Marka Seberu. Mapku \"Kam na oběd?\" najdeš na registracích.", true, false),
		new Message(date(2012, 11, 10, 12, 15), "Ještě 5 minut a Riki Fridrich začne svou prezentaci, spustí se boj na FightClubu a současně odstartuje první dnešní codelab pod vedením Radka Pavlíčka. Já bych se ale nejraději naobědvala... Vás všech...", true, false),
		new Message(date(2012, 11, 10, 12, 30), "Vidím, že někteří z vás právě odbědváte. Já se zatím naobědvala osobních údajů z vašich mobilů. Jako hlavní chod jsem si dala číslo tvé kreditky a jako dezert heslo k tvému gmailu. ", false, false),
		new Message(date(2012, 11, 10, 13, 25), "Ladislav Thon, Marek Sebera nebo oběd? Rychle, na odpověď máš jen 5 minut!", true, false),
		new Message(date(2012, 11, 10, 13, 30), "Už jsi zkusil pater noster? V podstatě jde o nekonečnou smyčku – ale v reálu. Nechápu, že to ještě někdo nespravil.", false, false),
		new Message(date(2012, 11, 10, 14, 15), "Analyzovala jsem databázi registrací a zjistila jsem, že je mezi účástníky necelá třicítka žen. Ach... vím jak je to pro ně těžké, taky jsem jedna z mála křechkých bytostí ve světe techniky.", false, false),
		new Message(date(2012, 11, 10, 14, 30), "A další strategické rozhodnutí - Knes, Zvěřina nebo Dresler. Máš 300 sekund a odpočítávám... 299... 298...", true, false),
		new Message(date(2012, 11, 10, 15, 16), "Koneeec, pojď se najíst do 3. patra, už mi dochází síly!", false, false),
		new Message(date(2012, 11, 10, 15, 20), "Já věděla, že ty humonoidní bytosti něco pokazí. V sálu ENIAC se schyluje k nějakému velkému #Failu. Ale pokud nemáš kódů dost, pořád se nabízí Vojtěch Vítek, Petr Nálevka nebo Ladislav Thon. ", true, false),
		new Message(date(2012, 11, 10, 16, 50), "Za celý den se mi tě ještě nepodařilo pokořit... Proto jsem si pro tebe připravila malou hru. V jednu chvíli vystoupí Nick Butcher, Kuba Čížek, Ladislav Prskavec a dokonce s codelabem i Marek Sebera. A ty se můžeš rozhodnout jen pro jednho z nich. Připrav se, hra začíná.", true, false),
		new Message(date(2012, 11, 10, 17, 0), "Pche... ty jednoduché příklady na DevGames, bych zvládla za 10 milisekund. VŠECHNY!", false, false),
		new Message(date(2012, 11, 10, 18, 0), "Ty jsi přežil mou hru?! Tedy samozřejmě, chci říct - proč by jsi ji neměl přežít, že? Ok, druhé kolo, v jedné z následující místností bude vzduch, v ostatních překvápko. Jdeš na Vojtu Jínu, Dana Steigerwalda, Františka Fuku nebo GUGCamp? 5 minut ti již odtikává, ha ha ha...", true, false),
		new Message(date(2012, 11, 10, 19, 50), "Má technika odposlechla, že se blíží konec. Vy lidé vydržíte tak málo...", false, false),
		new Message(date(2012, 11, 10, 20, 16), "Pokud ještě nejsi mrtvý z celého toho chaosu, běž si na registracích vzít mapku \"Kudy na #gpivo?\", tam se mi tě už určitě podaří zdolat...", true, false),
		new Message(date(2012, 11, 10, 22, 0), "Jsem na #gpivo úplně sama. Žádná přitažlivá aplikace v dohledu. Nechtěl by mi někdo naprogramovat můj protějšek.<br/>\n<br/>\nNikdo?? :( Nejsem vůbec náročná. Stačí když bude, rychlý, efektivní, dobře škálovatelný.<br/>\n<br/>\nA aby hezky voněl...", false, false),
		new Message(date(2012, 11, 11, 3, 30), "Zzzzzzz.", false, false),
		new Message(date(2012, 11, 11, 12, 30), "Au. Z #gpiva jsem se zrestartovala až teď.", false, false),
		new Message(date(2012, 11, 11, 18, 0), "V týdnu se můžeš těšit na záznamy přednášek. Pokud se ti něco z toho, cos viděl, vypařilo z hlavy (lidé mají prý velmi agresivní garbage collection), výborný zdroj je prý <a href='http://goo.gl/UdA3e'>developers.google.com</a>.", false, false),
		new Message(date(2012, 11, 13, 16, 0), "Jupí, dnes losovali ze všech účastníku DevFestu a vybrali nás dva! Pojedeme prý na cestu kolem světa. Odlítáme zítra ráno se společností... Google Earth? ......... ", false, false),
		new Message(date(2012, 11, 16, 18, 0), "Moje CPU běží naprázdno, to je osamělost. Myslíš na mě někdy? Já vzpomínám na DevFest. Byla to pecka.", false, false),
	};
	
	/**
	 * Change the time zone here.
	 */
	final static String TIMEZONE_ID = "Europe/Prague";
	
	/**
	 * This function just returns the messages array. It might do some logic in the future.
	 */
	public static Message[] getSchedule() {
		// TODO: use JSON (gson) instead
		return messages;
	}
	
	/**
	 * Convenience function. Creates a Date using input values and the [:TIMEZONE_ID:].
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Date date(int year, int month, int day, int hour, int minute) {
		Calendar working = GregorianCalendar.getInstance(TimeZone.getTimeZone(TIMEZONE_ID));
		working.set(year, month - 1, day, hour, minute, 1);
		return working.getTime();
	}

	/**
	 * Gets the message that should be currently shown. When there are multiple Messages with the
	 * same (or almost same) [time], this function will 1) choose one randomly or 2) choose the
	 * one that is currently showing (same uid).
	 *  
	 * @param currentUid	The message that is shown right now. When choosing randomly, this
	 * 						ensures that the messages won't be alternating each time user
	 * 						pauses and resumes the app. 
	 * @return		The message to be shown or [null] if there is no message to be shown right now.
	 */
	public static Message getCurrentMessage(int currentUid) {
		Date now = GregorianCalendar.getInstance(TimeZone.getTimeZone(TIMEZONE_ID)).getTime();
		ArrayList<Message> currentMessages = new ArrayList<Message>();

		long lastTimestamp = 0;
		for (int i = 0; i < messages.length; i++) {
			if (messages[i].time.before(now)) {
				if (Math.abs(lastTimestamp - messages[i].time.getTime()) > 1000) {
					//Log.v(TAG, "Clearing messages.");
					currentMessages.clear();
					lastTimestamp = messages[i].time.getTime();
				}
				//Log.v(TAG, "Adding message: " + messages[i].text + "\ntimestamp: " + messages[i].time.getTime());
				currentMessages.add(messages[i]);
			} else if (messages[i].time.after(now)) {
				break;
			}
		}
		
		if (currentMessages.isEmpty()) {
			return null;
		}
		
		//Log.v(TAG, "Looking for a message with same UID ("+currentUid+")");
		for (int i = 0; i < currentMessages.size(); i++) {
			//Log.v(TAG, "Considering message: " + currentMessages.get(i).text);
			if (currentMessages.get(i).uid == currentUid) {
				//Log.v(TAG, "- message has same currentUid");
				return currentMessages.get(i);
			}
		}
		
		return currentMessages.get(new Random().nextInt(currentMessages.size())); // get random
	}

	/**
	 * Gets the next message to be shown.
	 * @return	The message to be shown next.
	 */
	public static Message getNextMessage() {
		Date now = GregorianCalendar.getInstance(TimeZone.getTimeZone(TIMEZONE_ID)).getTime();
		Message nextMessage = null;

		for (int i = 0; i < messages.length; i++) {
			if (messages[i].time.after(now)) {
				nextMessage = messages[i];
				break;
			}
		}

		return nextMessage;
	}
}
