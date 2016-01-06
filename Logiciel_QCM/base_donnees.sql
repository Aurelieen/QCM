DROP TABLE IF EXISTS "Classe";
CREATE TABLE Classe
(
   id_classe             int not null,
   nom_classe            VARCHAR(255),
   primary key (id_classe)
);
DROP TABLE IF EXISTS "Enseignant";
CREATE TABLE Enseignant
(
   id_enseignant         int not null,
   nom_enseignant        VARCHAR(255),
   prenom_enseignant     VARCHAR(255),
   password_enseignant   CHAR(128),
   primary key (id_enseignant)
);
DROP TABLE IF EXISTS "Enseigne_dans";
CREATE TABLE Enseigne_dans
(
   id_enseignant         int not null,
   id_classe             int not null,
   primary key (id_enseignant, id_classe)
   foreign key (id_enseignant) REFERENCES Enseignant(id_enseignant),
   foreign key (id_classe) REFERENCES Classe(id_classe)
);
DROP TABLE IF EXISTS "Etudiant";
CREATE TABLE Etudiant
(
   id_etudiant           int not null,
   id_classe             int not null,
   nom_etudiant          VARCHAR(255),
   prenom_etudiant       VARCHAR(255),
   password_etudiant     CHAR(128),
   primary key (id_etudiant)
   foreign key (id_classe) REFERENCES Classe(id_classe)
);
DROP TABLE IF EXISTS "QCM";
CREATE TABLE QCM
(
   id_enseignant         int not null,
   id_classe             int not null,
   id_qcm                int not null,
   nom_qcm               VARCHAR(255),
   description_qcm       VARCHAR(255),
   date_debut            TIMESTAMP,
   date_fin              TIMESTAMP,
   conformite_relative   bool,
   primary key (id_enseignant, id_classe, id_qcm),
   foreign key (id_enseignant) REFERENCES Enseignant(id_enseignant),
   foreign key (id_classe) REFERENCES Classe(id_classe)
);
DROP TABLE IF EXISTS "Question";
CREATE TABLE Question
(
   
   id_qcm                int not null,
   id_question           int not null,
   description_question  VARCHAR(255),
   primary key (id_qcm, id_question),
   foreign key (id_qcm) REFERENCES QCM(id_qcm)
);
DROP TABLE IF EXISTS "Repond_a";
CREATE TABLE Repond_a
(
  
   id_qcm                int not null,
   id_etudiant           int not null,
   note                 float,
   primary key (id_qcm, id_etudiant)
  
   foreign key (id_qcm) REFERENCES QCM (id_qcm),
   foreign key (id_etudiant) REFERENCES Etudiant(id_etudiant)
);
DROP TABLE IF EXISTS "Reponse";
CREATE TABLE Reponse
(
  
   id_question           int not null,
   id_reponse            int not null,
   description_reponse   VARCHAR(255),
   est_juste             bool,
   primary key (id_question, id_reponse)
   foreign key (id_question) REFERENCES Question(id_question)
);
