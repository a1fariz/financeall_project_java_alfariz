--
-- PostgreSQL database cluster dump
--

-- Started on 2026-05-18 16:05:00

\restrict Iooxer9DJZJ0LFZAj9oZfeNNFKhPUURmDYVRERJUl0KUvaQBghCLLLHkpFm506P

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;

--
-- User Configurations
--








\unrestrict Iooxer9DJZJ0LFZAj9oZfeNNFKhPUURmDYVRERJUl0KUvaQBghCLLLHkpFm506P

--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

\restrict hP6dRe2w4nEWPab1efAiNMNkmxqFiKgUaQAZeph2GLPzshi9mcj1M7JBC8SEPfx

-- Dumped from database version 18.2
-- Dumped by pg_dump version 18.2

-- Started on 2026-05-18 16:05:00

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Completed on 2026-05-18 16:05:00

--
-- PostgreSQL database dump complete
--

\unrestrict hP6dRe2w4nEWPab1efAiNMNkmxqFiKgUaQAZeph2GLPzshi9mcj1M7JBC8SEPfx

--
-- Database "MediCall_db" dump
--

--
-- PostgreSQL database dump
--

\restrict f69mYVaYVnlnjadKBW0WjxfQ9UYS7NTBthIWQkx2KD6gEW210qmeRSrsyUQmpfZ

-- Dumped from database version 18.2
-- Dumped by pg_dump version 18.2

-- Started on 2026-05-18 16:05:01

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4923 (class 1262 OID 16473)
-- Name: MediCall_db; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "MediCall_db" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Indonesian_Indonesia.1252';


ALTER DATABASE "MediCall_db" OWNER TO postgres;

\unrestrict f69mYVaYVnlnjadKBW0WjxfQ9UYS7NTBthIWQkx2KD6gEW210qmeRSrsyUQmpfZ
\connect "MediCall_db"
\restrict f69mYVaYVnlnjadKBW0WjxfQ9UYS7NTBthIWQkx2KD6gEW210qmeRSrsyUQmpfZ

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 222 (class 1259 OID 16538)
-- Name: doctors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctors (
    id bigint NOT NULL,
    nama_dokter character varying(255) NOT NULL,
    spesialis character varying(255) NOT NULL,
    jadwal_tersedia timestamp without time zone NOT NULL
);


ALTER TABLE public.doctors OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16537)
-- Name: doctors_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.doctors_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.doctors_id_seq OWNER TO postgres;

--
-- TOC entry 4924 (class 0 OID 0)
-- Dependencies: 221
-- Name: doctors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.doctors_id_seq OWNED BY public.doctors.id;


--
-- TOC entry 220 (class 1259 OID 16525)
-- Name: reservations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reservations (
    id bigint NOT NULL,
    doctor_id bigint NOT NULL,
    nama_pasien character varying(255) NOT NULL,
    email_pasien character varying(255) NOT NULL,
    waktu_konsultasi timestamp without time zone NOT NULL,
    status character varying(255) DEFAULT 'SUCCESS'::character varying
);


ALTER TABLE public.reservations OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16524)
-- Name: reservations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reservations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reservations_id_seq OWNER TO postgres;

--
-- TOC entry 4925 (class 0 OID 0)
-- Dependencies: 219
-- Name: reservations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reservations_id_seq OWNED BY public.reservations.id;


--
-- TOC entry 4762 (class 2604 OID 16548)
-- Name: doctors id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctors ALTER COLUMN id SET DEFAULT nextval('public.doctors_id_seq'::regclass);


--
-- TOC entry 4760 (class 2604 OID 16560)
-- Name: reservations id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations ALTER COLUMN id SET DEFAULT nextval('public.reservations_id_seq'::regclass);


--
-- TOC entry 4917 (class 0 OID 16538)
-- Dependencies: 222
-- Data for Name: doctors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.doctors (id, nama_dokter, spesialis, jadwal_tersedia) FROM stdin;
1	dr. Alfarizi	Spesialis Jantung	2026-03-25 09:00:00
2	dr. Hafiz	Spesialis Anak	2026-03-25 13:00:00
\.


--
-- TOC entry 4915 (class 0 OID 16525)
-- Dependencies: 220
-- Data for Name: reservations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reservations (id, doctor_id, nama_pasien, email_pasien, waktu_konsultasi, status) FROM stdin;
1	1	Alfarizi	ratutuanratu@gmail.com	2026-03-25 09:00:00	SUCCESS
2	1	venzy	muhamadhafiz462@gmail.com	2026-03-25 14:00:00	SUCCESS
3	2	venzy	uts.java.budi@mailinator.com	2026-03-25 14:00:00	SUCCESS
4	1	alfa	uts.java.budi@mailinator.com	2026-03-25 14:22:59	SUCCESS
5	2	alfa	uts.java.budi@mailinator.com	2026-03-14 14:22:59	SUCCESS
6	2	al	uts.java.budi@mailinator.com	2026-03-13 14:23:00	SUCCESS
\.


--
-- TOC entry 4926 (class 0 OID 0)
-- Dependencies: 221
-- Name: doctors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.doctors_id_seq', 2, true);


--
-- TOC entry 4927 (class 0 OID 0)
-- Dependencies: 219
-- Name: reservations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reservations_id_seq', 6, true);


--
-- TOC entry 4766 (class 2606 OID 16550)
-- Name: doctors doctors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT doctors_pkey PRIMARY KEY (id);


--
-- TOC entry 4764 (class 2606 OID 16562)
-- Name: reservations reservations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservations_pkey PRIMARY KEY (id);


-- Completed on 2026-05-18 16:05:01

--
-- PostgreSQL database dump complete
--

\unrestrict f69mYVaYVnlnjadKBW0WjxfQ9UYS7NTBthIWQkx2KD6gEW210qmeRSrsyUQmpfZ

--
-- Database "financeall" dump
--

--
-- PostgreSQL database dump
--

\restrict 8u1mhKRFQMNpTOyQ6CbYA4jb2C1eIgUVXNVMfzu9RzN62v1LCKmnIiTIqGqPUmh

-- Dumped from database version 18.2
-- Dumped by pg_dump version 18.2

-- Started on 2026-05-18 16:05:01

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5038 (class 1262 OID 16635)
-- Name: financeall; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE financeall WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Indonesian_Indonesia.1252';


ALTER DATABASE financeall OWNER TO postgres;

\unrestrict 8u1mhKRFQMNpTOyQ6CbYA4jb2C1eIgUVXNVMfzu9RzN62v1LCKmnIiTIqGqPUmh
\connect financeall
\restrict 8u1mhKRFQMNpTOyQ6CbYA4jb2C1eIgUVXNVMfzu9RzN62v1LCKmnIiTIqGqPUmh

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 25483)
-- Name: admin_logs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.admin_logs (
    id bigint NOT NULL,
    "timestamp" timestamp(6) without time zone,
    user_id bigint,
    action character varying(255),
    details character varying(255)
);


ALTER TABLE public.admin_logs OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 25482)
-- Name: admin_logs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.admin_logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.admin_logs_id_seq OWNER TO postgres;

--
-- TOC entry 5039 (class 0 OID 0)
-- Dependencies: 219
-- Name: admin_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.admin_logs_id_seq OWNED BY public.admin_logs.id;


--
-- TOC entry 222 (class 1259 OID 25493)
-- Name: announcements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.announcements (
    created_at timestamp(6) without time zone,
    id bigint NOT NULL,
    content text,
    title character varying(255)
);


ALTER TABLE public.announcements OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 25492)
-- Name: announcements_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.announcements_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.announcements_id_seq OWNER TO postgres;

--
-- TOC entry 5040 (class 0 OID 0)
-- Dependencies: 221
-- Name: announcements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.announcements_id_seq OWNED BY public.announcements.id;


--
-- TOC entry 224 (class 1259 OID 25503)
-- Name: debt_items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.debt_items (
    due_date date,
    interest_rate numeric(19,2) DEFAULT 0.00,
    paid_amount numeric(19,2) DEFAULT 0.00,
    total_amount numeric(19,2) DEFAULT 0.00,
    id bigint NOT NULL,
    user_id bigint,
    creditor character varying(255),
    name character varying(255)
);


ALTER TABLE public.debt_items OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 25502)
-- Name: debt_items_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.debt_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.debt_items_id_seq OWNER TO postgres;

--
-- TOC entry 5041 (class 0 OID 0)
-- Dependencies: 223
-- Name: debt_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.debt_items_id_seq OWNED BY public.debt_items.id;


--
-- TOC entry 226 (class 1259 OID 25516)
-- Name: debt_payment_schedules; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.debt_payment_schedules (
    amount numeric(38,2),
    due_date date,
    paid boolean NOT NULL,
    debt_item_id bigint,
    id bigint NOT NULL
);


ALTER TABLE public.debt_payment_schedules OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 25515)
-- Name: debt_payment_schedules_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.debt_payment_schedules_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.debt_payment_schedules_id_seq OWNER TO postgres;

--
-- TOC entry 5042 (class 0 OID 0)
-- Dependencies: 225
-- Name: debt_payment_schedules_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.debt_payment_schedules_id_seq OWNED BY public.debt_payment_schedules.id;


--
-- TOC entry 228 (class 1259 OID 25525)
-- Name: emergency_funds; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.emergency_funds (
    current_amount numeric(38,2),
    target_amount numeric(38,2),
    id bigint NOT NULL,
    user_id bigint
);


ALTER TABLE public.emergency_funds OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 25524)
-- Name: emergency_funds_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.emergency_funds_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.emergency_funds_id_seq OWNER TO postgres;

--
-- TOC entry 5043 (class 0 OID 0)
-- Dependencies: 227
-- Name: emergency_funds_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.emergency_funds_id_seq OWNED BY public.emergency_funds.id;


--
-- TOC entry 230 (class 1259 OID 25535)
-- Name: fi_records; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fi_records (
    monthly_expense numeric(38,2),
    passive_income numeric(38,2),
    record_date date,
    id bigint NOT NULL,
    user_id bigint
);


ALTER TABLE public.fi_records OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 25534)
-- Name: fi_records_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fi_records_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.fi_records_id_seq OWNER TO postgres;

--
-- TOC entry 5044 (class 0 OID 0)
-- Dependencies: 229
-- Name: fi_records_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fi_records_id_seq OWNED BY public.fi_records.id;


--
-- TOC entry 232 (class 1259 OID 25543)
-- Name: levels; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.levels (
    required_points integer,
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.levels OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 25542)
-- Name: levels_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.levels_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.levels_id_seq OWNER TO postgres;

--
-- TOC entry 5045 (class 0 OID 0)
-- Dependencies: 231
-- Name: levels_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.levels_id_seq OWNED BY public.levels.id;


--
-- TOC entry 234 (class 1259 OID 25551)
-- Name: transaction_records; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transaction_records (
    amount numeric(38,2),
    transaction_date date,
    id bigint NOT NULL,
    user_id bigint,
    category character varying(255),
    description character varying(255),
    title character varying(255),
    type character varying(255),
    CONSTRAINT transaction_records_category_check CHECK (((category)::text = ANY ((ARRAY['MAKANAN'::character varying, 'TRANSPORT'::character varying, 'HIBURAN'::character varying, 'TAGIHAN'::character varying, 'GAJI'::character varying, 'LAINNYA'::character varying])::text[]))),
    CONSTRAINT transaction_records_type_check CHECK (((type)::text = ANY ((ARRAY['INCOME'::character varying, 'EXPENSE'::character varying])::text[])))
);


ALTER TABLE public.transaction_records OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 25550)
-- Name: transaction_records_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transaction_records_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.transaction_records_id_seq OWNER TO postgres;

--
-- TOC entry 5046 (class 0 OID 0)
-- Dependencies: 233
-- Name: transaction_records_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transaction_records_id_seq OWNED BY public.transaction_records.id;


--
-- TOC entry 236 (class 1259 OID 25563)
-- Name: user_level_progress; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_level_progress (
    current_points integer NOT NULL,
    id bigint NOT NULL,
    level_id bigint,
    user_id bigint
);


ALTER TABLE public.user_level_progress OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 25562)
-- Name: user_level_progress_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_level_progress_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_level_progress_id_seq OWNER TO postgres;

--
-- TOC entry 5047 (class 0 OID 0)
-- Dependencies: 235
-- Name: user_level_progress_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_level_progress_id_seq OWNED BY public.user_level_progress.id;


--
-- TOC entry 238 (class 1259 OID 25574)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    is_banned boolean NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    id bigint NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    role character varying(20) NOT NULL,
    username character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    full_name character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    recovery_pin character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 25573)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 5048 (class 0 OID 0)
-- Dependencies: 237
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 240 (class 1259 OID 25596)
-- Name: wallets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.wallets (
    balance numeric(19,2) DEFAULT 0.00,
    monthly_limit numeric(19,2) DEFAULT 0.00,
    id bigint NOT NULL,
    user_id bigint,
    wallet_name character varying(255)
);


ALTER TABLE public.wallets OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 25595)
-- Name: wallets_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.wallets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.wallets_id_seq OWNER TO postgres;

--
-- TOC entry 5049 (class 0 OID 0)
-- Dependencies: 239
-- Name: wallets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.wallets_id_seq OWNED BY public.wallets.id;


--
-- TOC entry 4805 (class 2604 OID 25486)
-- Name: admin_logs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin_logs ALTER COLUMN id SET DEFAULT nextval('public.admin_logs_id_seq'::regclass);


--
-- TOC entry 4806 (class 2604 OID 25496)
-- Name: announcements id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.announcements ALTER COLUMN id SET DEFAULT nextval('public.announcements_id_seq'::regclass);


--
-- TOC entry 4810 (class 2604 OID 25509)
-- Name: debt_items id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.debt_items ALTER COLUMN id SET DEFAULT nextval('public.debt_items_id_seq'::regclass);


--
-- TOC entry 4811 (class 2604 OID 25519)
-- Name: debt_payment_schedules id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.debt_payment_schedules ALTER COLUMN id SET DEFAULT nextval('public.debt_payment_schedules_id_seq'::regclass);


--
-- TOC entry 4812 (class 2604 OID 25528)
-- Name: emergency_funds id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emergency_funds ALTER COLUMN id SET DEFAULT nextval('public.emergency_funds_id_seq'::regclass);


--
-- TOC entry 4813 (class 2604 OID 25538)
-- Name: fi_records id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fi_records ALTER COLUMN id SET DEFAULT nextval('public.fi_records_id_seq'::regclass);


--
-- TOC entry 4814 (class 2604 OID 25546)
-- Name: levels id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.levels ALTER COLUMN id SET DEFAULT nextval('public.levels_id_seq'::regclass);


--
-- TOC entry 4815 (class 2604 OID 25554)
-- Name: transaction_records id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction_records ALTER COLUMN id SET DEFAULT nextval('public.transaction_records_id_seq'::regclass);


--
-- TOC entry 4816 (class 2604 OID 25566)
-- Name: user_level_progress id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_level_progress ALTER COLUMN id SET DEFAULT nextval('public.user_level_progress_id_seq'::regclass);


--
-- TOC entry 4817 (class 2604 OID 25577)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4820 (class 2604 OID 25601)
-- Name: wallets id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wallets ALTER COLUMN id SET DEFAULT nextval('public.wallets_id_seq'::regclass);


--
-- TOC entry 5012 (class 0 OID 25483)
-- Dependencies: 220
-- Data for Name: admin_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.admin_logs (id, "timestamp", user_id, action, details) FROM stdin;
\.


--
-- TOC entry 5014 (class 0 OID 25493)
-- Dependencies: 222
-- Data for Name: announcements; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.announcements (created_at, id, content, title) FROM stdin;
\.


--
-- TOC entry 5016 (class 0 OID 25503)
-- Dependencies: 224
-- Data for Name: debt_items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.debt_items (due_date, interest_rate, paid_amount, total_amount, id, user_id, creditor, name) FROM stdin;
\.


--
-- TOC entry 5018 (class 0 OID 25516)
-- Dependencies: 226
-- Data for Name: debt_payment_schedules; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.debt_payment_schedules (amount, due_date, paid, debt_item_id, id) FROM stdin;
\.


--
-- TOC entry 5020 (class 0 OID 25525)
-- Dependencies: 228
-- Data for Name: emergency_funds; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.emergency_funds (current_amount, target_amount, id, user_id) FROM stdin;
0.00	0.00	1	2
\.


--
-- TOC entry 5022 (class 0 OID 25535)
-- Dependencies: 230
-- Data for Name: fi_records; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fi_records (monthly_expense, passive_income, record_date, id, user_id) FROM stdin;
\.


--
-- TOC entry 5024 (class 0 OID 25543)
-- Dependencies: 232
-- Data for Name: levels; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.levels (required_points, id, name) FROM stdin;
0	1	Newbie
100	2	Saver
500	3	Investor
\.


--
-- TOC entry 5026 (class 0 OID 25551)
-- Dependencies: 234
-- Data for Name: transaction_records; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transaction_records (amount, transaction_date, id, user_id, category, description, title, type) FROM stdin;
\.


--
-- TOC entry 5028 (class 0 OID 25563)
-- Dependencies: 236
-- Data for Name: user_level_progress; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_level_progress (current_points, id, level_id, user_id) FROM stdin;
0	1	1	2
\.


--
-- TOC entry 5030 (class 0 OID 25574)
-- Dependencies: 238
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (is_banned, created_at, id, updated_at, role, username, email, full_name, password, recovery_pin) FROM stdin;
f	2026-05-18 15:50:58.79962	2	2026-05-18 15:50:58.79962	USER	alfariz	muhamadhafiz462@gmail.com	alfarizi	$2a$10$ZCxL2J1JqmSqVwuGtCoh7erod7NSv8DIHmQExkQLGK3nNNpQmMr3y	123456
f	2026-05-18 15:48:09.541383	1	2026-05-18 15:48:09.541383	ROLE_ADMIN	admin	admin@financeall.com	Super Admin	$2a$12$WZXJfBx2BYtwp.5oAfuDAOAXStc6JSE0GnduVL.4Vim5PxAjoth4u	123456
\.


--
-- TOC entry 5032 (class 0 OID 25596)
-- Dependencies: 240
-- Data for Name: wallets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.wallets (balance, monthly_limit, id, user_id, wallet_name) FROM stdin;
0.00	0.00	1	2	\N
\.


--
-- TOC entry 5050 (class 0 OID 0)
-- Dependencies: 219
-- Name: admin_logs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.admin_logs_id_seq', 1, false);


--
-- TOC entry 5051 (class 0 OID 0)
-- Dependencies: 221
-- Name: announcements_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.announcements_id_seq', 1, false);


--
-- TOC entry 5052 (class 0 OID 0)
-- Dependencies: 223
-- Name: debt_items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.debt_items_id_seq', 1, false);


--
-- TOC entry 5053 (class 0 OID 0)
-- Dependencies: 225
-- Name: debt_payment_schedules_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.debt_payment_schedules_id_seq', 1, false);


--
-- TOC entry 5054 (class 0 OID 0)
-- Dependencies: 227
-- Name: emergency_funds_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.emergency_funds_id_seq', 1, true);


--
-- TOC entry 5055 (class 0 OID 0)
-- Dependencies: 229
-- Name: fi_records_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fi_records_id_seq', 1, false);


--
-- TOC entry 5056 (class 0 OID 0)
-- Dependencies: 231
-- Name: levels_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.levels_id_seq', 3, true);


--
-- TOC entry 5057 (class 0 OID 0)
-- Dependencies: 233
-- Name: transaction_records_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transaction_records_id_seq', 1, false);


--
-- TOC entry 5058 (class 0 OID 0)
-- Dependencies: 235
-- Name: user_level_progress_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_level_progress_id_seq', 1, true);


--
-- TOC entry 5059 (class 0 OID 0)
-- Dependencies: 237
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 3, true);


--
-- TOC entry 5060 (class 0 OID 0)
-- Dependencies: 239
-- Name: wallets_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.wallets_id_seq', 1, true);


--
-- TOC entry 4824 (class 2606 OID 25491)
-- Name: admin_logs admin_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin_logs
    ADD CONSTRAINT admin_logs_pkey PRIMARY KEY (id);


--
-- TOC entry 4826 (class 2606 OID 25501)
-- Name: announcements announcements_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.announcements
    ADD CONSTRAINT announcements_pkey PRIMARY KEY (id);


--
-- TOC entry 4828 (class 2606 OID 25514)
-- Name: debt_items debt_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.debt_items
    ADD CONSTRAINT debt_items_pkey PRIMARY KEY (id);


--
-- TOC entry 4830 (class 2606 OID 25523)
-- Name: debt_payment_schedules debt_payment_schedules_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.debt_payment_schedules
    ADD CONSTRAINT debt_payment_schedules_pkey PRIMARY KEY (id);


--
-- TOC entry 4832 (class 2606 OID 25531)
-- Name: emergency_funds emergency_funds_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emergency_funds
    ADD CONSTRAINT emergency_funds_pkey PRIMARY KEY (id);


--
-- TOC entry 4834 (class 2606 OID 25533)
-- Name: emergency_funds emergency_funds_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emergency_funds
    ADD CONSTRAINT emergency_funds_user_id_key UNIQUE (user_id);


--
-- TOC entry 4836 (class 2606 OID 25541)
-- Name: fi_records fi_records_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fi_records
    ADD CONSTRAINT fi_records_pkey PRIMARY KEY (id);


--
-- TOC entry 4838 (class 2606 OID 25549)
-- Name: levels levels_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.levels
    ADD CONSTRAINT levels_pkey PRIMARY KEY (id);


--
-- TOC entry 4840 (class 2606 OID 25561)
-- Name: transaction_records transaction_records_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction_records
    ADD CONSTRAINT transaction_records_pkey PRIMARY KEY (id);


--
-- TOC entry 4842 (class 2606 OID 25570)
-- Name: user_level_progress user_level_progress_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_level_progress
    ADD CONSTRAINT user_level_progress_pkey PRIMARY KEY (id);


--
-- TOC entry 4844 (class 2606 OID 25572)
-- Name: user_level_progress user_level_progress_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_level_progress
    ADD CONSTRAINT user_level_progress_user_id_key UNIQUE (user_id);


--
-- TOC entry 4846 (class 2606 OID 25594)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4848 (class 2606 OID 25590)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4850 (class 2606 OID 25592)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 4852 (class 2606 OID 25604)
-- Name: wallets wallets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wallets
    ADD CONSTRAINT wallets_pkey PRIMARY KEY (id);


--
-- TOC entry 4854 (class 2606 OID 25606)
-- Name: wallets wallets_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wallets
    ADD CONSTRAINT wallets_user_id_key UNIQUE (user_id);


--
-- TOC entry 4855 (class 2606 OID 25607)
-- Name: admin_logs fk5xn2pg5opbtkhwxroddfqj9bt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin_logs
    ADD CONSTRAINT fk5xn2pg5opbtkhwxroddfqj9bt FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4857 (class 2606 OID 25617)
-- Name: debt_payment_schedules fk8ibvjabkeqgv9ehiypl7dflqw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.debt_payment_schedules
    ADD CONSTRAINT fk8ibvjabkeqgv9ehiypl7dflqw FOREIGN KEY (debt_item_id) REFERENCES public.debt_items(id);


--
-- TOC entry 4863 (class 2606 OID 25647)
-- Name: wallets fkc1foyisidw7wqqrkamafuwn4e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wallets
    ADD CONSTRAINT fkc1foyisidw7wqqrkamafuwn4e FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4856 (class 2606 OID 25612)
-- Name: debt_items fkcb3vv1w2gvdj3hsgjwxn4sl31; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.debt_items
    ADD CONSTRAINT fkcb3vv1w2gvdj3hsgjwxn4sl31 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4861 (class 2606 OID 25642)
-- Name: user_level_progress fkdvb9x76olan6jjf2b7nvimb1s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_level_progress
    ADD CONSTRAINT fkdvb9x76olan6jjf2b7nvimb1s FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4859 (class 2606 OID 25627)
-- Name: fi_records fkfr9fjr46edjsib9y7ovy3a9am; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fi_records
    ADD CONSTRAINT fkfr9fjr46edjsib9y7ovy3a9am FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4858 (class 2606 OID 25622)
-- Name: emergency_funds fkiege52qf5u79m2f4bes1kks5e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emergency_funds
    ADD CONSTRAINT fkiege52qf5u79m2f4bes1kks5e FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4862 (class 2606 OID 25637)
-- Name: user_level_progress fkmo7o9prt4llp47800vnn292v2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_level_progress
    ADD CONSTRAINT fkmo7o9prt4llp47800vnn292v2 FOREIGN KEY (level_id) REFERENCES public.levels(id);


--
-- TOC entry 4860 (class 2606 OID 25632)
-- Name: transaction_records fknxrii35nemvlgjc27yj4xukmb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction_records
    ADD CONSTRAINT fknxrii35nemvlgjc27yj4xukmb FOREIGN KEY (user_id) REFERENCES public.users(id);


-- Completed on 2026-05-18 16:05:01

--
-- PostgreSQL database dump complete
--

\unrestrict 8u1mhKRFQMNpTOyQ6CbYA4jb2C1eIgUVXNVMfzu9RzN62v1LCKmnIiTIqGqPUmh

-- Completed on 2026-05-18 16:05:01

--
-- PostgreSQL database cluster dump complete
--

