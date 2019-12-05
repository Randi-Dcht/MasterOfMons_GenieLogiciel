package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.GraphicalObjects.QuestShower;
import be.ac.umons.sgl.mom.Objects.Characters.Attack;
import be.ac.umons.sgl.mom.Objects.Characters.PNJ;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Master.Bachelor1;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Arrays;


/**
 *Cette classe permet de surveiller le jeu en temps réelle et gère en fonction des règles.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */

public class GameNormaly
{
        /*joueur dans cette partie*/
        private People people;
        /*liste des objets présent sur toutes les maps*/
        private ArrayList<Items> objet = new ArrayList<Items>();
        /*liste des personnages ordinateur*/
        private ArrayList<PNJ> listPNJ = new ArrayList<PNJ>();
        /*Interface graphique pour cette partie*/
        private QuestShower questShower;
        /*chaque 10 minutes*/
        private double minute = 600;
        /*sauveguarde du jeux*/
        private Saving save;
        /*temps du jeu*/
        private Schedule time;
        //private ArrayList<Events> events = new ArrayList<Events>;

        public  People getPeople()
        {
            return people;
        }

        public void add(PNJ ... lst)
        {
            listPNJ.addAll(Arrays.asList(lst));
        }

        public void add(Items ... lst)
        {
            objet.addAll(Arrays.asList(lst));
        }

        /**
         * Permet de créer une nouvelle partie du jeu.
         * Elle permet d'instancier les classes nécessaire
         * @param namePlayer qui est le pseudo du joueur
         * @param type qui est le type de personnage (fort,maigre,...)
         * @param graphical qui est l'affiche graphique du jeu
         * */
        public void newParty(String namePlayer, Type type, QuestShower graphical, GraphicalSettings gs)
        {
            questShower = graphical;
            people = new People(namePlayer,type);
            MasterQuest mQ = new Bachelor1(people,null);
            people.newQuest(mQ);
            questShower.setQuest(mQ);
            time = new Schedule(9,1,8,2019);
            save = new Saving(people,namePlayer,gs);
        }

        /**
         * Permet de dire que le joueur change de quêtes et appelle l'interface graphique
         * */
        public void changedQuest()
        {
            if(questShower != null) //permet lors des tests de ne pas intancier de classes graphique.
            {
                Gdx.app.postRunnable(() -> questShower.setQuest(people.getQuest()));
                save.Signal();
            }
        }

        /**
         * Cette méthode permet d'appeler régulièrement la méthode énergie du joueur
         * */
        public void callMethod(double dt)
        {
            if(people != null) /*cette méthode permet diminuer l'énergie du joueur*/
                people.energy(dt); //pour le joueur

            if(objet != null) /*cette méthode permet de diminuer la vie de l'objet*/
            {
                for (Items o : objet){o.make(dt);}//pour chaques objets
            }
            minute = minute - dt;
            if(minute <= 0) /*permet de faire une sauvguarde de temps en temps automatiquement*/
            {
                save.Signal();
                minute = 600;
            }

            time.updateSecond(0);
            //System.out.println("Temps jeu: " + time); /*<= pour tester le temps du jeu*/
        }
    }