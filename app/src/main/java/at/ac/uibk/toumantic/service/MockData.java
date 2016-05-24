package at.ac.uibk.toumantic.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import at.ac.uibk.toumantic.model.Offer;

/**
 * Created by david on 5/24/16.
 */
public class MockData {
    public static final Offer[] OFFERS = {
            new Offer(
                    "Wellness mit Aussicht",
                    "Im Spa entspannen und dabei kristallklare Seen oder eindrucksvolle Berge ins Visier nehmen: Einige Wellnessresorts bieten nicht nur Erholung für Körper und Seele, sondern auch ein außergewöhnliches Panorama.",
                    "http://www.austria.info/media/17083/thumbnails/mountain-resort-feuerberg-kaernten--mrf.jpg.3073101.jpg",
                    new String[]{"Day-Spa"},
                    119.0f
            ),
            new Offer(
                    "Alpine Wellness",
                    "Klare Höhenluft, markantes Gipfelpanorama, satte Almwiesen. Der Wohlfühlfaktor der Bergidylle lässt sich aber noch steigern. Wie wär’s mit Relaxliege, Blubberbad und Sauna? Authentisches Ambiente und die Verwendung von Naturprodukten zählen überdies zu den Erfolgsfaktoren von alpiner Wellness.",
                    "http://www.austria.info/media/17083/thumbnails/Ebners-Waldhof-am-See-Quellgarten-c-ww-ebners-waldhof.jpg.3109914.jpg",
                    new String[]{"Day-Spa"},
                    119.0f
            ),
            new Offer(
                    "Entspannen in der Therme",
                    "Sich im Wasser treiben lassen, auf der Sprudelliege entspannen, durch Panoramafenster die Berge betrachten. Im Wohlfühl-Ambiente exklusiver Thermenlandschaften lässt es sich herrlich erholen und mit der Seele baumeln.",
                    "http://www.austria.info/media/17083/thumbnails/Aussenansicht_2_c_Rogner%20Bad%20Blumau.jpg.3115281.jpg",
                    new String[]{"Day-Spa", "Therme"},
                    29.90f
            ),
            new Offer(
                    "Schloss Schönbrunn",
                    "Einst als Jagdschloss mit weitläufigem Park errichtet, wurde Schloss Schönbrunn ab Mitte des 18. Jahrhunderts zu seiner jetzigen Größe ausgebaut und war Sommersitz und Residenzschloss der kaiserlichen Familie und des Hofstaats.",
                    "http://www.austria.info/media/17083/thumbnails/schloss-schoenbrunn-und-gloriette-oesterreich-werbung-Julius%20Silver.jpg.3184063.jpg",
                    new String[]{"Museum", "Historisch"},
                    4.99f
            ),
            new Offer(
                    "Kunsthistorisches Museum",
                    "Das Kunsthistorische Museum ist nicht nur ein Ort für die Kunst, es ist auch Kunstwerk für sich. 1891 öffnete der monumentale Prachtbau an der Wiener Ringstraße mit seinem prunkvollen Interieur aus Stuck und Marmor zum ersten Mal seine Pforten.",
                    "http://www.austria.info/media/17083/thumbnails/00000016022-kunsthistorisches-museum-wien-1-oesterreich-werbung-Trumler.jpg.3067005.jpg",
                    new String[]{"Museum", "Kunst"},
                    4.99f
            ),
            new Offer(
                    "Technisches Museum Wien",
                    "Die Erfindung der Schiffsschraube wird einem Österreicher zugeschrieben. Das Technische Museum Wien zeigt Exponate und Modelle aus der Geschichte der Technik unter besonderer Berücksichtigung Österreichs.",
                    "http://www.austria.info/media/17083/thumbnails/technisches-museum-flugzeuge--tm.jpg.3143669.jpg",
                    new String[]{"Museum"},
                    9.99f
            ),
            new Offer(
                    "MAK",
                    "Das MAK verfügt über eine einzigartige Sammlung angewandter Kunst, Design, Architektur und Gegenwartskunst, die im Laufe von 150 Jahren entstanden ist und herausragende Exponate aus verschiedenen Stilepochen präsentiert.",
                    "http://www.austria.info/media/17083/thumbnails/museum-fuer-angewandte-kunst-oesterreich-werbung-Mayer.jpg.3146192.jpg",
                    new String[]{"Museum"},
                    4.99f
            ),
            new Offer(
                    "Natur Eis Palast",
                    "Parallelwelt aus purem Eis: Der Natur Eis Palast ist eine natürlich gewachsene Höhle im Hintertuxer Gletscher bei Hintertux, rund 25 Meter unter den Skipisten der Gefrorenen Wand.",
                    "http://www.austria.info/media/17083/thumbnails/natur-eispalast-tirol--zillertaler-gletscherbahnen.jpg.3045424.jpg",
                    new String[]{"Erkundung"},
                    4.99f
            ),
            new Offer(
                    "Innsbrucker Nordkettenbahnen",
                    "Nirgendwo sonst liegt die Grenze zwischen urbanem Raum und rauer Bergwelt so nah zusammen: Mit den Nordkettenbahnen gelangt man in nur 20 Minuten vom Innsbrucker Stadtzentrum in hochalpines Gelände.",
                    "http://www.austria.info/media/17083/thumbnails/innsbruck-seilbahn-hafelekar--innsbruck-tourismus-christof-lackner.jpg.3183053.jpg",
                    new String[]{"Landschaft"},
                    15.00f
            ),
            new Offer(
                    "Schloss Ambras",
                    "Wo Erzherzog Ferdinand II einst seiner Rolle als Renaissancefürst voll und ganz gerecht wurde, indem er die Künste und Wissenschaften förderte und in 18 deckenhohen Kästen eine Urform heutiger Museen errichtete, wird dieser Fördergedanke bis heute ungebrochen fortgesetzt.",
                    "http://www.austria.info/media/17083/thumbnails/schloss-ambras-spanischer-saal--tvb-innsbruck-bernhard-aichner.jpg.3097300.jpg",
                    new String[]{"Schloss"},
                    10.00f
            ),
            new Offer(
                    "Mozart-Wohnhaus",
                    "1773 übersiedelte die Familie Mozart in eine geräumige Achtzimmerwohnung auf dem heutigen Markartplatz, die den Rahmen für gesellschaftliche Anlässe bot. Hier schrieb der junge Wolfgang zahlreiche Werke, u.a. „Il Re pastore\" und Teile des \"Idomeneo“.",
                    "http://www.austria.info/media/17083/thumbnails/mozart-wohnhaus--salzburg-tourismus.jpg.3127650.jpg",
                    new String[]{"Historisch"},
                    14.00f
            ),
            new Offer(
                    "Kunsthaus Graz",
                    "Lange Zeit galt der Uhrturm als Symbol für Graz, doch mittlerweile hat die Stadt ein weiteres architektonisches Wahrzeichen erhalten: das Kunsthaus Graz, auch „Friendly Alien“ genannt.",
                    "http://www.austria.info/media/17083/thumbnails/kunsthaus-in-graz-oesterreich-werbung-Viennaslide.jpg.3127724.jpg",
                    new String[]{"Museum"},
                    24.00f
            )

    };
    public static final HashMap ID_MAPPING = new HashMap<String, Offer>();

    static {
        List<Offer> rand = new ArrayList<Offer>() {{
            for (Offer o : OFFERS)
                add(o);
        }};
        Collections.shuffle(rand);
        int id = 0;
        for (Offer o : rand) {
            o.setID(id++);
        }
        for (Offer o : OFFERS)
            ID_MAPPING.put(o.getID(), o);
    }
}
