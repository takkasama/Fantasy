/*
Created: 5/3/2026
Modified: 5/31/2026
Model: FantasyDefender
Database: Oracle 18c
*/




-- Create sequences section -------------------------------------------------

CREATE SEQUENCE fande_players_sq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 NOCACHE
;

CREATE SEQUENCE fande_castle_sq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 NOCACHE
;

CREATE SEQUENCE fande_crossbow_sq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 NOCACHE
;

CREATE SEQUENCE fande_game_sq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 NOCACHE
;

-- Create tables section -------------------------------------------------

-- Table fande_player

CREATE TABLE fande_player(
  ply_id Number NOT NULL,
  ply_name Varchar2(30 ) NOT NULL,
  ply_email Varchar2(80 ) NOT NULL,
  ply_password Varchar2(255 ) NOT NULL,
  ply_register_date Date NOT NULL,
  ply_avatar Blob,
  ply_avatar_type Varchar2(10 ),
  ply_version Number DEFAULT 1 NOT NULL
)
;

-- Create indexes for table fande_player

CREATE UNIQUE INDEX fande_player_ind01 ON fande_player (ply_id)
;

CREATE UNIQUE INDEX fande_player_ind02 ON fande_player (ply_email)
;

CREATE UNIQUE INDEX fande_player_ind03 ON fande_player (ply_name)
;

-- Add keys for table fande_player

ALTER TABLE fande_player ADD CONSTRAINT PK_fande_player PRIMARY KEY (ply_id)
;

-- Table and Columns comments section

COMMENT ON COLUMN fande_player.ply_id IS 'Player ID
'
;
COMMENT ON COLUMN fande_player.ply_name IS 'Player Name'
;
COMMENT ON COLUMN fande_player.ply_email IS 'Player Electronical Mail'
;
COMMENT ON COLUMN fande_player.ply_password IS 'Player Password'
;
COMMENT ON COLUMN fande_player.ply_register_date IS 'Player Register Date
'
;
COMMENT ON COLUMN fande_player.ply_avatar IS 'Player  Avatar (supported formats : .png , .jpg , .webp)'
;
COMMENT ON COLUMN fande_player.ply_avatar_type IS 'Player Avatar type ( .png , .jpg , .webp )'
;
COMMENT ON COLUMN fande_player.ply_version IS 'Player Table Version'
;
-- Table fande_castle

CREATE TABLE fande_castle(
  ctl_id Number NOT NULL,
  ctl_elixer_lvl Number NOT NULL,
  ctl_health_lvl Number NOT NULL,
  ctl_version Number DEFAULT 1 NOT NULL
)
;

-- Create indexes for table fande_castle

CREATE UNIQUE INDEX fande_castle_ind01 ON fande_castle (ctl_id)
;

-- Add keys for table fande_castle

ALTER TABLE fande_castle ADD CONSTRAINT PK_fande_castle PRIMARY KEY (ctl_id)
;

-- Table and Columns comments section

COMMENT ON COLUMN fande_castle.ctl_id IS 'Castle id 
'
;
COMMENT ON COLUMN fande_castle.ctl_elixer_lvl IS 'Castle elixer level
'
;
COMMENT ON COLUMN fande_castle.ctl_health_lvl IS 'Castle Health Level
'
;
COMMENT ON COLUMN fande_castle.ctl_version IS 'Castle Table Version'
;

-- Table fande_crossbow

CREATE TABLE fande_crossbow(
  crb_id Number NOT NULL,
  crb_damage_lvl Number NOT NULL,
  crb_frecuency_lvl Number NOT NULL,
  crb_version Number DEFAULT 1 NOT NULL,
  cbr_crossbow_select Char(1 CHAR) DEFAULT 'D' NOT NULL
)
;

-- Create indexes for table fande_crossbow

CREATE UNIQUE INDEX fande_crossbow_ind01 ON fande_crossbow (crb_id)
;

-- Add keys for table fande_crossbow

ALTER TABLE fande_crossbow ADD CONSTRAINT PK_fande_crossbow PRIMARY KEY (crb_id)
;

-- Table and Columns comments section

COMMENT ON COLUMN fande_crossbow.crb_id IS 'Crossbow Id '
;
COMMENT ON COLUMN fande_crossbow.crb_damage_lvl IS 'Crossbow Damage Level 

'
;
COMMENT ON COLUMN fande_crossbow.crb_frecuency_lvl IS 'Crossbow frecuency level '
;
COMMENT ON COLUMN fande_crossbow.crb_version IS 'Crossbow Table Version
'
;
COMMENT ON COLUMN fande_crossbow.cbr_crossbow_select IS 'Crossbow type [Defauld Value D (CrossbowDefault), Otrher crossbow D (default), S (Second) , T ( Third)]'
;

-- Table fande_game

CREATE TABLE fande_game(
  gm_id Number NOT NULL,
  crb_id Number NOT NULL,
  ctl_id Number NOT NULL,
  ply_id Number NOT NULL,
  gm_name Varchar2(50 ) NOT NULL,
  gm_level Number DEFAULT 1 NOT NULL,
  gm_difficulty Char(1 CHAR) DEFAULT 'N' NOT NULL,
  gm_points Number DEFAULT 0 NOT NULL,
  gm_ice_level Number DEFAULT 1 NOT NULL,
  gm_meteor_level Number DEFAULT 1 NOT NULL,
  gm_version Number DEFAULT 1 NOT NULL
)
;

-- Create indexes for table fande_game

CREATE UNIQUE INDEX fande_game_ind01 ON fande_game (gm_id)
;

CREATE UNIQUE INDEX fande_game_ind02 ON fande_game (crb_id)
;

CREATE UNIQUE INDEX fande_game_ind03 ON fande_game (ctl_id)
;

-- Add keys for table fande_game

ALTER TABLE fande_game ADD CONSTRAINT PK_fande_game PRIMARY KEY (gm_id)
;

-- Table and Columns comments section

COMMENT ON COLUMN fande_game.gm_id IS 'Game ID'
;
COMMENT ON COLUMN fande_game.gm_name IS 'Game Name'
;
COMMENT ON COLUMN fande_game.gm_level IS 'Game Current Level'
;
COMMENT ON COLUMN fande_game.gm_difficulty IS 'Game difficulty ( Normal N , Easy E , Hard H , Brutality''B ) ( Default N Normal )'
;
COMMENT ON COLUMN fande_game.gm_points IS 'Game Points '
;
COMMENT ON COLUMN fande_game.gm_ice_level IS 'Ice Special Attack Level'
;
COMMENT ON COLUMN fande_game.gm_meteor_level IS 'Meteor Special Attack Level'
;
COMMENT ON COLUMN fande_game.gm_version IS 'Game Table Version'
;


-- Trigger for sequence fande_ players_sq01 for column ply_id in table fande_player ---------
CREATE OR REPLACE TRIGGER fande_player_fande_players_tgr01 BEFORE INSERT
ON fande_player FOR EACH ROW
BEGIN
	IF :NEW.ply_id IS NULL OR :NEW.ply_id <= 0 THEN
  		:new.ply_id := fande_players_sq01.nextval;
	END IF;
END;

CREATE OR REPLACE TRIGGER fande_player_fande_players_tgr02 AFTER UPDATE OF ply_id
ON fande_player FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column ply_id in table fande_player as it uses sequence.');
END;


-- Trigger for sequence fande_castle_sq01 for column ctl_id in table fande_castle ---------
CREATE OR REPLACE TRIGGER fande_castle_fande_castle_tgr01 BEFORE INSERT
ON fande_castle FOR EACH ROW
BEGIN
	IF :NEW.ctl_id IS NULL OR :NEW.ctl_id <= 0 THEN
	  	:new.ctl_id := fande_castle_sq01.nextval;
	END IF;
END;

CREATE OR REPLACE TRIGGER fande_castle_fande_castle_tgr02 AFTER UPDATE OF ctl_id
ON fande_castle FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column ctl_id in table fande_castle as it uses sequence.');
END;


-- Trigger for sequence fande_crossbow_sq01 for column crb_id in table fande_crossbow ---------
CREATE OR REPLACE TRIGGER fande_crossbow_fande_crossbow_tgr01 BEFORE INSERT
ON fande_crossbow FOR EACH ROW
BEGIN
	IF :NEW.crb_id IS NULL OR :NEW.crb_id <= 0 THEN
 		:new.crb_id := fande_crossbow_sq01.nextval;
	END IF;
END;

CREATE OR REPLACE TRIGGER fande_crossbow_fande_crossbow_tgr02 AFTER UPDATE OF crb_id
ON fande_crossbow FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column crb_id in table fande_crossbow as it uses sequence.');
END;


-- Trigger for sequence fande_game_sq01 for column gp_id in table fande_game_progress ---------
CREATE OR REPLACE TRIGGER fande_game_fande_game_tgr01 BEFORE INSERT
ON fande_game FOR EACH ROW
BEGIN
	IF :NEW.gm_id IS NULL OR :NEW.gm_id <= 0 THEN
 		:new.gm_id := fande_game_sq01.nextval;
	END IF;
END;

CREATE OR REPLACE TRIGGER fande_game_fande_game_tgr02 AFTER UPDATE OF gm_id
ON fande_game FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column gm_id in table fande_game as it uses sequence.');
END;


-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE fande_game ADD CONSTRAINT fande_game_fk01 FOREIGN KEY (crb_id) REFERENCES fande_crossbow (crb_id)
;



ALTER TABLE fande_game ADD CONSTRAINT fande_game_fk02 FOREIGN KEY (ctl_id) REFERENCES fande_castle (ctl_id)
;



ALTER TABLE fande_game ADD CONSTRAINT fande_player_fk01 FOREIGN KEY (ply_id) REFERENCES fande_player (ply_id)
;







