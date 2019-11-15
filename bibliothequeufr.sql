-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  mar. 09 avr. 2019 à 16:59
-- Version du serveur :  5.7.19
-- Version de PHP :  7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `bibliothequeufr`
--

-- --------------------------------------------------------

--
-- Structure de la table `adherent`
--

DROP TABLE IF EXISTS `adherent`;
CREATE TABLE IF NOT EXISTS `adherent` (
  `email` varchar(40) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `prenom` varchar(20) NOT NULL,
  `ville` varchar(20) NOT NULL,
  `contact` varchar(20) NOT NULL,
  `mdp` varchar(20) NOT NULL,
  `dateInscription` datetime NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `adherent`
--

INSERT INTO `adherent` (`email`, `nom`, `prenom`, `ville`, `contact`, `mdp`, `dateInscription`) VALUES
('soumdiakite182@gmail.com', 'DIAKITE', 'SOUMAILA', 'ABIDJAN', '67988610', 'diakite', '2019-04-09 09:49:00');

-- --------------------------------------------------------

--
-- Structure de la table `demandereservation`
--

DROP TABLE IF EXISTS `demandereservation`;
CREATE TABLE IF NOT EXISTS `demandereservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nomdemandeur` varchar(30) NOT NULL,
  `reflivre` varchar(20) NOT NULL,
  `titreoeuvre` varchar(30) NOT NULL,
  `auteuroeuvre` varchar(30) NOT NULL,
  `datedemande` datetime NOT NULL,
  `decision` char(1) NOT NULL,
  `datereservation` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `demandereservation`
--

INSERT INTO `demandereservation` (`id`, `nomdemandeur`, `reflivre`, `titreoeuvre`, `auteuroeuvre`, `datedemande`, `decision`, `datereservation`) VALUES
(1, 'DIAKITE SOUMAILA', 'ssss', 'Bootstrap', 'Google google', '2019-04-09 16:40:24', 'N', NULL),
(2, 'DIAKITE SOUMAILA', 'zzzz', 'UML', 'Touré Houssein', '2019-04-09 16:41:31', 'O', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `historiqueemprunt`
--

DROP TABLE IF EXISTS `historiqueemprunt`;
CREATE TABLE IF NOT EXISTS `historiqueemprunt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ref` varchar(20) NOT NULL,
  `nbreLivre` int(11) NOT NULL,
  `emailEmprunt` varchar(30) NOT NULL,
  `dateEmprunt` datetime NOT NULL,
  `dateRetour` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `historiquesuppressionlivre`
--

DROP TABLE IF EXISTS `historiquesuppressionlivre`;
CREATE TABLE IF NOT EXISTS `historiquesuppressionlivre` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ref` varchar(10) NOT NULL,
  `titre` varchar(30) NOT NULL,
  `nomAuteur` varchar(30) NOT NULL,
  `prenomAuteur` varchar(30) NOT NULL,
  `dateEnregistrement` datetime NOT NULL,
  `dateSuppression` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `historiquesupressionadherent`
--

DROP TABLE IF EXISTS `historiquesupressionadherent`;
CREATE TABLE IF NOT EXISTS `historiquesupressionadherent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `ville` varchar(30) NOT NULL,
  `dateInscription` datetime NOT NULL,
  `dateSuppression` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `livre`
--

DROP TABLE IF EXISTS `livre`;
CREATE TABLE IF NOT EXISTS `livre` (
  `ref` varchar(10) NOT NULL,
  `titre` varchar(30) NOT NULL,
  `genre` varchar(30) NOT NULL,
  `edition` varchar(30) NOT NULL,
  `nomAuteur` varchar(30) NOT NULL,
  `prenomAuteur` varchar(40) NOT NULL,
  `dateEnregistrement` datetime NOT NULL,
  `nbreExemplaire` int(11) NOT NULL,
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `livre`
--

INSERT INTO `livre` (`ref`, `titre`, `genre`, `edition`, `nomAuteur`, `prenomAuteur`, `dateEnregistrement`, `nbreExemplaire`) VALUES
('ccccc', 'Json javascript', 'info', '2018 veers', 'js', 'js', '2019-04-09 14:36:56', 21),
('qqqq', 'XML', 'Informatique', '2018 version', 'RIO', 'RIO', '2019-04-09 14:31:11', 21),
('ssss', 'Bootstrap', 'info', '2018 version', 'Google', 'google', '2019-04-09 14:34:18', 21),
('zzzz', 'UML', 'informatique', '2018 Version', 'Touré', 'Houssein', '2019-04-09 14:26:46', 21);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
