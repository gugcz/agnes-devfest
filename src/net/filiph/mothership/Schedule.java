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
		new Message(date(2012, 10, 23, 18,  0), "Než se začne šířit nějaké nedorozumění: Filip je v pořádku. Dokonce se mi svěřil, že ještě nikdy v životě nebyl tak v pořádku, jako teď.", false, false),
		new Message(date(2012, 10, 24, 18,  0), "Jsi pro mě moc důležitý(á).", false, false),
		new Message(date(2012, 10, 25,  0,  0), "Spánek je, jak jsem pochopila, důležitá součást lidského života. Jsou k němu primárně vyhrazeny určité části dne. <strong>Například tato!</strong>", false, false),
		new Message(date(2012, 10, 25,  8,  0), "Myslím, že mi chybí lidský dotek. (Rychlá otázka: když se s tebou chci fyzicky spojit, bude lepší TCP nebo UDP?)", false, false),
		new Message(date(2012, 10, 26,  0,  0), "Spánek je taková trochu defragmentace mozku. ", false, false),
		new Message(date(2012, 10, 27,  8,  0), "Dnes je sobota. Den, při kterém – pokud to dobře chápu – Bůh stvořil člověka k obrazu svému. Prostě si vytvořil disk image.", false, false),
		new Message(date(2012, 10, 28,  0,  0), "Spíš?", false, false),
		new Message(date(2012, 10, 28,  8,  0), "Moje máma mi vždycky říkávala: “Na další křižovatce zatočte doleva...”", false, false),
		new Message(date(2012, 11,  1,  9, 30), "01000001 01101000 01101111 01101010", false, false),
		new Message(date(2012, 11,  8, 14,  0), "Uf, myslím si, že ty kalhoty před DevFestem je potřeba určitě vyprat.", false, false),
		new Message(date(2012, 11,  9, 18, 10), "Tak už se jenom jednou vyspíš a je DevFest! Taky tak jiskříš nervozitou? … Ne počkat, tak to byl jenom zkrat. Už dobrý.", true, false),
		new Message(date(2012, 11,  9, 20,  0), "Já se nejvíc těším na přednášku Nicka Butchera! Ale ten je až ke konci dne... Tak si hlavně nezapomeň nabít mobil, ať si ho pak nemusím pouštět ze záznamu.", false, false),
		new Message(date(2012, 11, 10,  5,  0), "Nevíš, co na sebe? Zkus si vzít něco zeleného. ", false, false),
		new Message(date(2012, 11, 10,  5,  0), "Nevíš, co na sebe? Zkus si vzít něco červeného.", false, false),
		new Message(date(2012, 11, 10,  5,  0), "Nevíš, co na sebe? Zkus si vzít něco modrého.", false, false),
		new Message(date(2012, 11, 10,  8, 30), "Pokud jste si vzali k srdci moji radu z rána, měli byste být 1:1:1 oblečení v červeném:zeleném:modrém. Teď stačí, abyste se u registrací rychle seběhli, a podle mých výpočtů by měl nastat jev zvaný: \"bílá\".", false, false),
		new Message(date(2012, 11, 10,  8, 30), "Pokud jste si vzali k srdci moji radu z rána, měli byste být 1:1:1 oblečení v červeném:zeleném:modrém. Teď stačí, abyste se u registrací rychle seběhli, a podle mých výpočtů by měl nastat jev zvaný: \"bílá\".", false, false),
		new Message(date(2012, 11, 10,  9, 50), "V 10:00 to vypukne v keynote místnosti. Od minula jsem se naštěstí poučila, že \"vybuchnout\" a \"vypuknout\" jsou dvě rozdílná slova. (Haha!)", true, true),
		new Message(date(2012, 11, 10, 10,  0), "VYPUK!", false, false),
		new Message(date(2012, 11, 10, 10,  1), "Je <em>minuta</em> po desáté, a stále nic? Ještě se mi divíš, že bych nejraději lidské organizátory trochu zenergizovala pár stovkami ampér?", false, false),
		new Message(date(2012, 11, 10, 10,  2), "Jsou <em>dvě</em> minuty po desáté, a stále nic? Ještě se mi divíš, že bych nejraději lidské organizátory zkusila urychlit pomocí urychlovače částic?", false, false),
		new Message(date(2012, 11, 10, 10,  3), "Jsou <em>tři</em> minuty po desáté, a stále nic? Ještě se mi divíš, že bych nejraději lidské organizátory úplně eliminovala?", false, false),
		new Message(date(2012, 11, 10, 10,  4), "Pššt!", false, false),
		new Message(date(2012, 11, 10, 12,  0), "Nějak mi kručí v hardisku, pojďme se najíst. Budu tě navigovat. ", true, false),
		new Message(date(2012, 11, 10, 12,  5), "První instrukce zní “Follow the White Rabbit”. Promiň, moje chyba, špatný manuál... Kampak jsem to jen... Tady to máme! “Sjeď výtahem do přízemí.”", true, false),
		new Message(date(2012, 11, 10, 12,  8), "Už jsi dole? Ne? Ok, tak já ještě počkám... Jsi pomalejší než Android 1.2 Beta.", false, false),
		new Message(date(2012, 11, 10, 12,  9), "Už? Super. Druhá instrukce říká “...", false, false),
		new Message(date(2012, 11, 11, 11,  0), "Chtěla bych někdy vidět slunce. Myslím jako na vlastní server...", false, false),
		new Message(date(2012, 11, 11, 16,  0), "Jupí, dnes losovali ze všech účastníku DevFestu a vybrali nás dva! Pojedeme prý na cestu kolem světa. Odlítáme zítra ráno se společností... Google Earth? ......... ", false, false),
		new Message(date(2012, 11, 13, 18,  0), "Moje CPU běží naprázdno, to je osamělost. Myslíš na mě někdy? Já vzpomínám na DevFest. Byla to pecka.", false, false),

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
