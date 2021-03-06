PGDMP     %    9                y           petites_annonces    13.2    13.2 &    ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    16394    petites_annonces    DATABASE     l   CREATE DATABASE petites_annonces WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'French_France.1252';
     DROP DATABASE petites_annonces;
                postgres    false            ?           0    0    DATABASE petites_annonces    ACL     9   GRANT CONNECT ON DATABASE petites_annonces TO "appUser";
                   postgres    false    3041            ?           0    0    SCHEMA public    ACL     +   GRANT USAGE ON SCHEMA public TO "appUser";
                   postgres    false    3            ?            1259    24592    Annonce    TABLE     ?   CREATE TABLE public."Annonce" (
    id_annonce integer NOT NULL,
    titre text,
    description text,
    id_annonceur integer,
    image bytea,
    localisation text,
    prix double precision,
    categorie text
);
    DROP TABLE public."Annonce";
       public         heap    postgres    false            ?           0    0    TABLE "Annonce"    ACL     C   GRANT SELECT,INSERT,DELETE ON TABLE public."Annonce" TO "appUser";
          public          postgres    false    204            ?            1259    24622    Annonce_id_annonce_seq    SEQUENCE     ?   ALTER TABLE public."Annonce" ALTER COLUMN id_annonce ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Annonce_id_annonce_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 100000
    CACHE 1
);
            public          postgres    false    204            ?            1259    24608    AnnoncesSaves    TABLE     i   CREATE TABLE public."AnnoncesSaves" (
    id_profil integer NOT NULL,
    id_annonce integer NOT NULL
);
 #   DROP TABLE public."AnnoncesSaves";
       public         heap    postgres    false            ?           0    0    TABLE "AnnoncesSaves"    ACL     I   GRANT SELECT,INSERT,DELETE ON TABLE public."AnnoncesSaves" TO "appUser";
          public          postgres    false    206            ?            1259    24613    Messages    TABLE     ?   CREATE TABLE public."Messages" (
    id_profil_emetteur integer NOT NULL,
    id_profil_recepteur integer NOT NULL,
    date_envoie timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    message text
);
    DROP TABLE public."Messages";
       public         heap    postgres    false            ?           0    0    TABLE "Messages"    ACL     =   GRANT SELECT,INSERT ON TABLE public."Messages" TO "appUser";
          public          postgres    false    207            ?            1259    16397    Profil    TABLE     ?   CREATE TABLE public."Profil" (
    id integer NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    "contactPhone" text,
    "contactMail" text
);
    DROP TABLE public."Profil";
       public         heap    postgres    false            ?           0    0    TABLE "Profil"    ACL     ;   GRANT SELECT,INSERT ON TABLE public."Profil" TO "appUser";
          public          postgres    false    201            ?            1259    24584    Profil_Pers    TABLE     j   CREATE TABLE public."Profil_Pers" (
    id integer NOT NULL,
    "firstName" text,
    "lastName" text
);
 !   DROP TABLE public."Profil_Pers";
       public         heap    postgres    false            ?           0    0    TABLE "Profil_Pers"    ACL     @   GRANT SELECT,INSERT ON TABLE public."Profil_Pers" TO "appUser";
          public          postgres    false    203            ?            1259    24576 
   Profil_Pro    TABLE     h   CREATE TABLE public."Profil_Pro" (
    id integer NOT NULL,
    "companyName" text,
    website text
);
     DROP TABLE public."Profil_Pro";
       public         heap    postgres    false            ?           0    0    TABLE "Profil_Pro"    ACL     ?   GRANT SELECT,INSERT ON TABLE public."Profil_Pro" TO "appUser";
          public          postgres    false    202            ?            1259    16395    Profile_id_seq    SEQUENCE     ?   ALTER TABLE public."Profil" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Profile_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 100000
    CACHE 1
);
            public          postgres    false    201            ?            1259    24600    ReportAnnonce    TABLE     q   CREATE TABLE public."ReportAnnonce" (
    id_profil integer NOT NULL,
    id_annonce integer,
    raison text
);
 #   DROP TABLE public."ReportAnnonce";
       public         heap    postgres    false            ?           0    0    TABLE "ReportAnnonce"    ACL     B   GRANT SELECT,INSERT ON TABLE public."ReportAnnonce" TO "appUser";
          public          postgres    false    205            ?          0    24592    Annonce 
   TABLE DATA           w   COPY public."Annonce" (id_annonce, titre, description, id_annonceur, image, localisation, prix, categorie) FROM stdin;
    public          postgres    false    204   ?&       ?          0    24608    AnnoncesSaves 
   TABLE DATA           @   COPY public."AnnoncesSaves" (id_profil, id_annonce) FROM stdin;
    public          postgres    false    206   ?'       ?          0    24613    Messages 
   TABLE DATA           c   COPY public."Messages" (id_profil_emetteur, id_profil_recepteur, date_envoie, message) FROM stdin;
    public          postgres    false    207   (       ?          0    16397    Profil 
   TABLE DATA           Y   COPY public."Profil" (id, username, password, "contactPhone", "contactMail") FROM stdin;
    public          postgres    false    201   ?(       ?          0    24584    Profil_Pers 
   TABLE DATA           D   COPY public."Profil_Pers" (id, "firstName", "lastName") FROM stdin;
    public          postgres    false    203   ?)       ?          0    24576 
   Profil_Pro 
   TABLE DATA           B   COPY public."Profil_Pro" (id, "companyName", website) FROM stdin;
    public          postgres    false    202   ?)       ?          0    24600    ReportAnnonce 
   TABLE DATA           H   COPY public."ReportAnnonce" (id_profil, id_annonce, raison) FROM stdin;
    public          postgres    false    205   *       ?           0    0    Annonce_id_annonce_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public."Annonce_id_annonce_seq"', 5, true);
          public          postgres    false    208            ?           0    0    Profile_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public."Profile_id_seq"', 4, true);
          public          postgres    false    200            J           2606    24599    Annonce Annonce_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public."Annonce"
    ADD CONSTRAINT "Annonce_pkey" PRIMARY KEY (id_annonce);
 B   ALTER TABLE ONLY public."Annonce" DROP CONSTRAINT "Annonce_pkey";
       public            postgres    false    204            N           2606    24612     AnnoncesSaves AnnoncesSaves_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public."AnnoncesSaves"
    ADD CONSTRAINT "AnnoncesSaves_pkey" PRIMARY KEY (id_profil, id_annonce);
 N   ALTER TABLE ONLY public."AnnoncesSaves" DROP CONSTRAINT "AnnoncesSaves_pkey";
       public            postgres    false    206    206            P           2606    24626    Messages Messages_pkey 
   CONSTRAINT     ?   ALTER TABLE ONLY public."Messages"
    ADD CONSTRAINT "Messages_pkey" PRIMARY KEY (id_profil_emetteur, id_profil_recepteur, date_envoie);
 D   ALTER TABLE ONLY public."Messages" DROP CONSTRAINT "Messages_pkey";
       public            postgres    false    207    207    207            H           2606    24591    Profil_Pers Profile_Pers_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY public."Profil_Pers"
    ADD CONSTRAINT "Profile_Pers_pkey" PRIMARY KEY (id);
 K   ALTER TABLE ONLY public."Profil_Pers" DROP CONSTRAINT "Profile_Pers_pkey";
       public            postgres    false    203            F           2606    24583    Profil_Pro Profile_Pro_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public."Profil_Pro"
    ADD CONSTRAINT "Profile_Pro_pkey" PRIMARY KEY (id);
 I   ALTER TABLE ONLY public."Profil_Pro" DROP CONSTRAINT "Profile_Pro_pkey";
       public            postgres    false    202            D           2606    16404    Profil Profile_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY public."Profil"
    ADD CONSTRAINT "Profile_pkey" PRIMARY KEY (id);
 A   ALTER TABLE ONLY public."Profil" DROP CONSTRAINT "Profile_pkey";
       public            postgres    false    201            L           2606    24607     ReportAnnonce ReportAnnonce_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public."ReportAnnonce"
    ADD CONSTRAINT "ReportAnnonce_pkey" PRIMARY KEY (id_profil);
 N   ALTER TABLE ONLY public."ReportAnnonce" DROP CONSTRAINT "ReportAnnonce_pkey";
       public            postgres    false    205            ?   ?   x???;N?@?k?> ?6??ZRD?I3?q"K??d??sp1[ ?K#KS|????.p\g?????L?G~_	???>_??#??n9??EeڨJ???54???@h>T~?I???\8??$???e??ʶ?|?Y}~???5>.?0]?J?%N?Z?V??}hVR]?q??l?^恽???؄9??%??'??O?B#?>??U)h3????av?,l1?/??49??%'mr=A?Oվ?>M???1}z      ?      x?3?4?2?4?????? $?      ?   ?   x?}?1N?0E???Q?9c'??ABpJ?l2?WY;?8+jN?؛p?T,b?܌?????B??ުf?(?Q??VJ???UX?k????W??|?H???R$?;????V??Y?p????Г?%x????kqj??6;?K7"Xsf`?w~?O??y????Q???zp<?????7??x??7?.??????A6?ߠ????Z?????;ҚC??)???@b<WeY~	?my      ?   ?   x?M??
?0Eד?)MM??,"?| ????	m0ɔtD??+????;?H?LB??m?7???ELL?z?%R??????y6?MG???? ??x9????;? ??Z???????(??#???w?lӵø'j??<??f?/?3!?I?:q      ?       x?3??/??t)M??2?I,I??c???? z?      ?   !   x?3?t?M????,//?K3???s?b???? ?!?      ?       x?3?4?tK,-.NUH????KN?????? V	?     