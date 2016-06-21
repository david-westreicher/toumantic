package at.ac.uibk.toumantic.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import at.ac.uibk.toumantic.model.Offer;

/**
 * Created by david on 5/24/16.
 */
public class MockData {
    public static final List<Offer> OFFERS = createOffers();

    private static List<Offer> createOffers() {
        List<Offer> offers = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            offers.add(createLodging(i));
        for (int i = 0; i < 4; i++)
            offers.add(createEvent(i));
        for (int i = 0; i < 1; i++)
            offers.add(createOffer(i));
        for (int i = 0; i < 4; i++)
            offers.add(createRestaurant(i));
        for (int i = 0; i < 4; i++)
            offers.add(createTouristAttraction(i));
        return offers;
    }

    private static Offer createTouristAttraction(int i) {
        Offer o = new Offer();
        o.type = Offer.OfferType.TouristAttraction;
        fillPlace(o);
        switch (i) {
            case 0:
                o.name = "Schloss Schönbrunn";
                o.description = "Einst als Jagdschloss mit weitläufigem Park errichtet, wurde Schloss Schönbrunn ab Mitte des 18. Jahrhunderts zu seiner jetzigen Größe ausgebaut und war Sommersitz und Residenzschloss der kaiserlichen Familie und des Hofstaats.";
                o.image = "http://www.austria.info/media/17083/thumbnails/schloss-schoenbrunn-und-gloriette-oesterreich-werbung-Julius%20Silver.jpg.3184063.jpg";
                o.geo = new Offer.Geo(48.185115, 16.312283);
                o.address = "Schönbrunner Schloßstraße 47, 1130 Wien";
                o.telephone = "0181 113239";
                o.action = "https://www.imperial-austria.at/schloss-schoenbrunn.html";
                break;
            case 1:
                o.name = "Mozart-Wohnhaus";
                o.description = "1773 übersiedelte die Familie Mozart in eine geräumige Achtzimmerwohnung auf dem heutigen Markartplatz, die den Rahmen für gesellschaftliche Anlässe bot. Hier schrieb der junge Wolfgang zahlreiche Werke, u.a. \"Il Re pastore\" und Teile des \"Idomeneo\".";
                o.image = "http://www.austria.info/media/17083/thumbnails/mozart-wohnhaus--salzburg-tourismus.jpg.3127650.jpg";
                o.geo = new Offer.Geo(47.802900, 13.043848);
                o.address = "Makartpl. 8, 5020 Salzburg";
                o.telephone = "0662 87422740";
                o.action = "http://www.mozarteum.at/shop";
                break;
            case 2:
                o.name = "Schloss Ambras";
                o.description = "Wo Erzherzog Ferdinand II einst seiner Rolle als Renaissancefürst voll und ganz gerecht wurde, indem er die Künste und Wissenschaften förderte und in 18 deckenhohen Kästen eine Urform heutiger Museen errichtete, wird dieser Fördergedanke bis heute ungebrochen fortgesetzt.";
                o.image = "http://www.austria.info/media/17083/thumbnails/schloss-ambras-spanischer-saal--tvb-innsbruck-bernhard-aichner.jpg.3097300.jpg";
                o.geo = new Offer.Geo(47.256765, 11.433619);
                o.address = "Schloßstraße 20, 6020 Innsbruck";
                o.telephone = "0152 5244802";
                o.action = "https://shop.khm.at/tickets/";
                break;
            case 3:
                o.name = "Kunsthaus Graz";
                o.description = "Lange Zeit galt der Uhrturm als Symbol für Graz, doch mittlerweile hat die Stadt ein weiteres architektonisches Wahrzeichen erhalten: das Kunsthaus Graz, auch \"Friendly Alien\" genannt.";
                o.image = "http://www.austria.info/media/17083/thumbnails/kunsthaus-in-graz-oesterreich-werbung-Viennaslide.jpg.3127724.jpg";
                o.geo = new Offer.Geo(47.071642, 15.434084);
                o.address = "Lendkai 1, 8020 Graz";
                o.telephone = "0316 80179200";
                o.action = "http://www.kunsthausgraz.at";
                break;
            default:
                o.description = "";
                o.image = "";
                o.name = "";
                break;
        }
        return o;
    }

    private static void fillPlace(Offer o) {
        o.address = "Innsbruck";
        o.telephone = "0512 - 5460465870";
    }

    private static Offer createRestaurant(int i) {
        Offer o = new Offer();
        o.type = Offer.OfferType.Restaurant;
        fillPlace(o);
        switch (i) {
            case 0:
                o.name = "Breakfast Club";
                o.description = "The Breakfast Club, the first one, the most brilliant one some would even call it the godfather of Brunch in Innsbruck. It surely sounds a bit stuck up but it’s just how the former mayor Hilde Zach used to say:” Innsbruck is a cosmopolitan city”. Therefore, we are trying to contribute bits and pieces from the great wide WORLD into our beloved home INNSBRUCK.";
                o.image = "http://tiroler-madl.at/wp-content/uploads/2015/04/S149834-1500x996.jpg";
                o.address = "Maria-Theresien-Straße 49, 6020 Innsbruck";
                o.action = "http://www.breakfast-club.at/reservations-day--17336234-en.html";
                break;
            case 1:
                o.name = "Rama";
                o.description = "Nettes kleines Restaurant mit gutem Essen";
                o.image = "http://assets.sta.io/site_media/u/mi/2013/05/08/thumb_w700_h800_A.JPG";
                o.address = "Innstraße 81, 6020 Innsbruck";
                o.action = "http://rama.stadtausstellung.at/";
                break;
            case 2:
                o.name = "Gasthof Weisses Rossl";
                o.description = "Das familiäre Hotel und das traditionelle Altstadtwirtshaus präsentieren sich mit viel Atmosphäre und Komfort als ein Ort des Genießens, Wohlfühlens und geselligen Entspannens.";
                o.image = "http://www.roessl.at/images/portfolio_images/roessl_stube_1.jpg";
                o.address = "Kiebachgasse 8, 6020 Innsbruck";
                o.action = "http://www.roessl.at/";
                break;
            case 3:
                o.name = "Goldener Adler";
                o.description = "Willkommen im ältesten Restaurant Innsbrucks. Prost und Mahlzeit! In den historischen Stuben werden kulinarische Köstlichkeiten aus Tirol und aus der ganzen Welt kredenzt Obligat mit dem herzlichen Gruß aus der Küche, einem Amuse-Gueule der Jahreszeit und der Inspiration unseres Küchenchefs entsprechend";
                o.image = "http://www.goldeneradler.com/imagetypes/header/goldeneradler_speisesaal.jpg";
                o.address = "Herzog-Friedrich-Straße 6, 6020 Innsbruck";
                o.action = "http://www.goldeneradler.com//";
                break;
            default:
                o.description = "";
                o.image = "";
                o.name = "";
                break;
        }
        o.geo = new Offer.Geo(47.266252, 11.392193);
        fillLocalBusiness(o);
        return o;
    }

    private static void fillLocalBusiness(Offer o) {
        o.openingHours = (int) ((Math.random() * 10) + 8) + ":00 - 22:00";
        o.priceRange = String.format("%.2f", ((int) (Math.random() * 20)) + 30.99f);
    }

    private static Offer createOffer(int i) {
        Offer o = new Offer();
        o.type = Offer.OfferType.Offer;
        o.image = "http://www.austria.info/media/17083/thumbnails/innsbruck-seilbahn-hafelekar--innsbruck-tourismus-christof-lackner.jpg.3183053.jpg";
        o.name = "Innsbrucker Nordkettenbahnen";
        o.items = new String[]{"-10%"};
        o.description = "Nirgendwo sonst liegt die Grenze zwischen urbanem Raum und rauer Bergwelt so nah zusammen: Mit den Nordkettenbahnen gelangt man in nur 20 Minuten vom Innsbrucker Stadtzentrum in hochalpines Gelände.";
        o.action = "http://www.nordkette.com/tarife.html";
        fillOffer(o);
        return o;
    }

    private static void fillOffer(Offer o) {
        o.price = (float) ((int) (Math.random() * 15)) + 5.99f;
        List<String> cats = Arrays.asList("Sport", "Music", "Theater", "Festival");
        Collections.shuffle(cats);
        o.category = cats.get(0);
    }

    private static Offer createEvent(int i) {
        Offer o = new Offer();
        o.type = Offer.OfferType.Event;
        fillEvent(o);
        switch (i) {
            case 0:
                o.name = "Tanzsommer Innsbruck";
                o.description = "Wer Sport und Tanz gleichermaßen liebt, bekommt während des TANZSOMMERS keine Terminprobleme. Parallel zum TANZSOMMER findet in Frankreich die Fußball Europameisterschaft statt. Die Beginnzeiten der Tanzvorstellungen sind an den EM-Spielplan angepasst. So sehen Fußball- und Tanzfans beides nacheinander live.";
                o.image = "http://www.innsbruck.info/emobilder/800cx350c/514/Hessisches-Staatsballett-I.jpg";
                o.location = "Dogana Innsbruck";
                o.action = "https://ticket.tanzsommer.at/live/Events.aspx?eventtypeid=1";
                break;
            case 1:
                o.name = "Downhill Cup Innsbruck";
                o.description = " Der Event ist bewusst als offener Bewerb angelegt, um die breite Masse der Hobby-Biker anzusprechen. Zugleich dient die neue Rennserie als Probelauf für künftige Mountainbike-Events, die das Sommerangebot Innsbrucks für seine sportlichen Gäste erweitern sollen.";
                o.image = "http://www.innsbruck.info/emobilder/800cx350c/20374/Nordkette-Singletrail.jpg";
                o.location = "Nordkette Innsbruck";
                o.action = "http://www.innsbruck.info/innsbruck-city/veranstaltungen/sportstadt-innsbruck/innsbruck-downhill-cup.html";
                break;
            case 2:
                o.name = "Illusionen";
                o.description = "Begeisterung für die Wahrnehmung schaffen und die faszinierende Welt der Sinne erlebbar machen - mit der neuen Sonderausstellung \"Illusionen - Täuschung der Sinne\" stellt das AUDIOVERSUM sein innovatives Vermittlungskonzept erneut unter Beweis.";
                o.image = "http://www.innsbruck.info/emobilder/800cx350c/1050/Abenteuer-H%C3%B6ren.jpg";
                o.location = "Audioversum Innsbruck";
                o.action = "http://www.innsbruck.info/veranstaltungen/detail/event/sonderausstellung-illusionen-taeuschung-der-sinne.html";
                break;
            case 3:
                o.name = "Holi Festival";
                o.description = "Über 12.000 Besucher kamen bisher in Innsbruck zum HOLI. Nun kommt es zum großen Finale mit zahlreichen Highlights. Das AREAL beim HAFEN wird 2016 daher wieder Autragungsort dieses bunten Spektakels sein. " +
                        "Noch größer und noch bunter als je zuvor. HOLI bedeutet: ein schönes, friedliches Fest mit viel Farbe auf Kleidung, Körper und in der Luft! Gemeinsam mit einem abwechslungsreichen Band und DJ Programm feiern wir ab 14:00 dieses bunte Festival. Einlass ist ab 16 Jahren. Lasst euch dieses bunte Finale nicht entgehen! ♥";
                o.image = "http://holifestival.com/files/userdata/images/nach_staedten/Berlin/Bild-Berlin-1.jpg";
                o.doorTime = "14:00";
                o.location = "Hafen Innsbruck";
                o.action = "http://holiopenair.at/gig/holi-innsbruck/";
                break;
            default:
                o.description = "";
                o.image = "";
                o.name = "";
                break;
        }
        return o;
    }

    private static void fillEvent(Offer o) {
        o.doorTime = (int) ((Math.random() * 4) + 8) + ":00";
        o.performer = "";
        o.startDate = randomDate();
    }

    private static String randomDate() {
        //TODO
        return "29.7.2016";
    }


    private static Offer createLodging(int i) {
        Offer o = new Offer();
        o.type = Offer.OfferType.LodgingBusiness;
        fillPlace(o);
        switch (i) {
            case 0:
                o.name = "The Penz Hotel";
                o.description = "Im Zentrum von Innsbruck." +
                        "Ein Haus, das alle Blicke auf sich zieht." +
                        "Mit klaren Linien und zeitloser Eleganz." +
                        "Angenehmes Licht und edle Materialien," +
                        "Großzügige Zimmer und Suiten - gelebte Architektur." +
                        "Ihr Design-Hotel.";
                o.image = "http://www.the-penz.com/uploads/tx_templavoila/penz.gallery.2_01.jpg";
                o.geo = new Offer.Geo(47.266252, 11.392193);
                o.address = "Adolf-Pichler-Platz 3, 6020 Innsbruck";
                o.action = "http://www.the-penz.com/";
                break;
            case 1:
                o.name = "Ibis Innsbruck";
                o.description = "Ob Wintersport, Sommerfrische oder Geschäftsreise, im Herzen Tirols ist vieles möglich. Im ibis Innsbruck wohnen Sie wenige Schritte vom Zentrum entfernt. Ideal für Termine in der Stadt. " +
                        " 8 Skigebiete sind direkt per Shuttlebus vom Hotel aus zu erreichen. Für Wanderer bieten sich vielfältige Touren in der Umgebung. Ihr Auto parkt gegen eine Gebühr in der Tiefgarage. In den 75 klimatisierten Zimmern erwartet Sie eine ansprechende Ausstattung mit den Annehmlichkeiten eines modernen Hotels.";
                o.image = "http://www.ahstatic.com/photos/5174_ho_00_p_1024x768.jpg";
                o.geo = new Offer.Geo(47.266252, 11.392193);
                o.address = "Sterzinger Strasse 1, 6020 Innsbruck";
                o.action = "http://www.ibis.com/en/hotel-5174-ibis-innsbruck/index.shtml";
                break;
            case 2:
                o.name = "Adlers Hotel";
                o.description = "Imposant und einzigartig ragt der 12-stöckige Tower in Innsbruck empor. Das aDLERS, Innsbrucks höchstes Hotel- & Gastronomie-Erlebnis, bietet seit 21. Juni 2013 den schönsten Ausblick über Innsbruck und die umliegende Bergwelt. In Augenhöhe mit der Nordkette thront man in der 130 qm großen Präsidentensuite mit 2 Schlafzimmern, Ankleideraum, 2 Bädern mit Dusche und Badewanne sowie eigener Bar.";
                o.image = "http://www.deradler.com/fileadmin/deradler/_processed_/csm_Hotel_Adlers_Hotel_Presse_01_b61e00f669.jpg";
                o.geo = new Offer.Geo(47.266252, 11.392193);
                o.address = "Brunecker Str. 1, 6020 Innsbruck";
                o.action = "http://www.adlers-innsbruck.com/";
                break;
            case 3:
                o.name = "Hotel Schwarzer Adler";
                o.description = "Treten Sie ein in ein Haus mit 500-jähriger Geschichte. Das persönlichste Hotel Innsbrucks erobert Ihr Herz in stilvollem Flair. Das traditionsreiche Hotel hat zahlreiche Umbauten erlebt. Deshalb gleicht keines der 40 Zimmer und Suiten dem anderen. Entdecken Sie Ihr Lieblingszimmer mit Stadt- oder Bergblick, traditionell oder modern, rustikal oder lieblich.";
                o.image = "http://www.schwarzeradler-innsbruck.com/fileadmin/romantikhotel/_processed_/csm_Aussenansicht_Schwarzer_Adler_Innsbruck_6668118bb2.jpg";
                o.geo = new Offer.Geo(47.266252, 11.392193);
                o.address = "Kaiserjägerstraße 2, 6020 Innsbruck";
                o.action = "http://www.adlers-innsbruck.com/";
                break;
            default:
                o.description = "";
                o.image = "";
                o.name = "";
                break;
        }
        fillLocalBusiness(o);
        return o;
    }

    public static final HashMap ID_MAPPING = new HashMap<String, Offer>();

    static {
        Collections.shuffle(OFFERS);
        int id = 0;
        for (Offer o : OFFERS) {
            o.setID(id++);
            assert o.image != null && o.image.length() != 0;
        }
        for (Offer o : OFFERS)
            ID_MAPPING.put(o.getID(), o);
    }
}
