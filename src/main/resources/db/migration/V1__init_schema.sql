-- Flyway baseline schema for FinanceAll
-- Generated from JPA entities (Hibernate ddl-auto=create) on a clean PostgreSQL 16.
-- This is the single source of truth for the database schema.

-- Name: admin_logs; Type: TABLE; Schema: public; Owner: -

CREATE TABLE public.admin_logs (
    created_at timestamp(6) without time zone NOT NULL,
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    action character varying(255) NOT NULL,
    detail text,
    ip_address character varying(255)
);

-- Name: admin_logs_id_seq; Type: SEQUENCE; Schema: public; Owner: -

CREATE SEQUENCE public.admin_logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Name: admin_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -

ALTER SEQUENCE public.admin_logs_id_seq OWNED BY public.admin_logs.id;

-- Name: announcements; Type: TABLE; Schema: public; Owner: -

CREATE TABLE public.announcements (
    created_at timestamp(6) without time zone,
    id bigint NOT NULL,
    content text,
    title character varying(255)
);

-- Name: announcements_id_seq; Type: SEQUENCE; Schema: public; Owner: -

CREATE SEQUENCE public.announcements_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Name: announcements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -

ALTER SEQUENCE public.announcements_id_seq OWNED BY public.announcements.id;

-- Name: debt_items; Type: TABLE; Schema: public; Owner: -

CREATE TABLE public.debt_items (
    amount numeric(38,2),
    due_date date,
    interest_rate numeric(38,2) NOT NULL,
    paid_amount numeric(38,2) NOT NULL,
    total_amount numeric(38,2) NOT NULL,
    id bigint NOT NULL,
    user_id bigint,
    creditor character varying(255),
    name character varying(255)
);

-- Name: debt_items_id_seq; Type: SEQUENCE; Schema: public; Owner: -

CREATE SEQUENCE public.debt_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Name: debt_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -

ALTER SEQUENCE public.debt_items_id_seq OWNED BY public.debt_items.id;

-- Name: debt_payment_schedules; Type: TABLE; Schema: public; Owner: -

CREATE TABLE public.debt_payment_schedules (
    amount numeric(38,2),
    due_date date,
    paid boolean NOT NULL,
    debt_item_id bigint,
    id bigint NOT NULL
);

-- Name: debt_payment_schedules_id_seq; Type: SEQUENCE; Schema: public; Owner: -

CREATE SEQUENCE public.debt_payment_schedules_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Name: debt_payment_schedules_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -

ALTER SEQUENCE public.debt_payment_schedules_id_seq OWNED BY public.debt_payment_schedules.id;

-- Name: emergency_funds; Type: TABLE; Schema: public; Owner: -

CREATE TABLE public.emergency_funds (
    current_amount numeric(38,2) NOT NULL,
    monthly_saving numeric(38,2) NOT NULL,
    target_amount numeric(38,2) NOT NULL,
    id bigint NOT NULL,
    user_id bigint
);

-- Name: emergency_funds_id_seq; Type: SEQUENCE; Schema: public; Owner: -

CREATE SEQUENCE public.emergency_funds_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Name: emergency_funds_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -

ALTER SEQUENCE public.emergency_funds_id_seq OWNED BY public.emergency_funds.id;

-- Name: fi_records; Type: TABLE; Schema: public; Owner: -

CREATE TABLE public.fi_records (
    annual_return numeric(38,2) NOT NULL,
    current_investment numeric(38,2) NOT NULL,
    estimated_years integer NOT NULL,
    fi_target numeric(38,2) NOT NULL,
    monthly_expense numeric(38,2) NOT NULL,
    monthly_investment numeric(38,2) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    id bigint NOT NULL,
    user_id bigint NOT NULL
);

-- Name: fi_records_id_seq; Type: SEQUENCE; Schema: public; Owner: -

CREATE SEQUENCE public.fi_records_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Name: fi_records_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -

ALTER SEQUENCE public.fi_records_id_seq OWNED BY public.fi_records.id;

-- Name: levels; Type: TABLE; Schema: public; Owner: -

CREATE TABLE public.levels (
    required_points integer,
    id bigint NOT NULL,
    name character varying(255)
);

-- Name: levels_id_seq; Type: SEQUENCE; Schema: public; Owner: -

CREATE SEQUENCE public.levels_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Name: levels_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -

ALTER SEQUENCE public.levels_id_seq OWNED BY public.levels.id;

-- Name: transaction_records; Type: TABLE; Schema: public; Owner: -

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

-- Name: transaction_records_id_seq; Type: SEQUENCE; Schema: public; Owner: -

CREATE SEQUENCE public.transaction_records_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Name: transaction_records_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -

ALTER SEQUENCE public.transaction_records_id_seq OWNED BY public.transaction_records.id;

-- Name: user_level_progress; Type: TABLE; Schema: public; Owner: -

CREATE TABLE public.user_level_progress (
    current_points integer NOT NULL,
    id bigint NOT NULL,
    level_id bigint,
    user_id bigint
);

-- Name: user_level_progress_id_seq; Type: SEQUENCE; Schema: public; Owner: -

CREATE SEQUENCE public.user_level_progress_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Name: user_level_progress_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -

ALTER SEQUENCE public.user_level_progress_id_seq OWNED BY public.user_level_progress.id;

-- Name: users; Type: TABLE; Schema: public; Owner: -

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

-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;

-- Name: wallets; Type: TABLE; Schema: public; Owner: -

CREATE TABLE public.wallets (
    balance numeric(38,2) NOT NULL,
    monthly_limit numeric(38,2) NOT NULL,
    id bigint NOT NULL,
    user_id bigint,
    CONSTRAINT wallets_check CHECK (((balance >= (0)::numeric) AND (monthly_limit >= (0)::numeric)))
);

-- Name: wallets_id_seq; Type: SEQUENCE; Schema: public; Owner: -

CREATE SEQUENCE public.wallets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Name: wallets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -

ALTER SEQUENCE public.wallets_id_seq OWNED BY public.wallets.id;

-- Name: admin_logs id; Type: DEFAULT; Schema: public; Owner: -

ALTER TABLE ONLY public.admin_logs ALTER COLUMN id SET DEFAULT nextval('public.admin_logs_id_seq'::regclass);

-- Name: announcements id; Type: DEFAULT; Schema: public; Owner: -

ALTER TABLE ONLY public.announcements ALTER COLUMN id SET DEFAULT nextval('public.announcements_id_seq'::regclass);

-- Name: debt_items id; Type: DEFAULT; Schema: public; Owner: -

ALTER TABLE ONLY public.debt_items ALTER COLUMN id SET DEFAULT nextval('public.debt_items_id_seq'::regclass);

-- Name: debt_payment_schedules id; Type: DEFAULT; Schema: public; Owner: -

ALTER TABLE ONLY public.debt_payment_schedules ALTER COLUMN id SET DEFAULT nextval('public.debt_payment_schedules_id_seq'::regclass);

-- Name: emergency_funds id; Type: DEFAULT; Schema: public; Owner: -

ALTER TABLE ONLY public.emergency_funds ALTER COLUMN id SET DEFAULT nextval('public.emergency_funds_id_seq'::regclass);

-- Name: fi_records id; Type: DEFAULT; Schema: public; Owner: -

ALTER TABLE ONLY public.fi_records ALTER COLUMN id SET DEFAULT nextval('public.fi_records_id_seq'::regclass);

-- Name: levels id; Type: DEFAULT; Schema: public; Owner: -

ALTER TABLE ONLY public.levels ALTER COLUMN id SET DEFAULT nextval('public.levels_id_seq'::regclass);

-- Name: transaction_records id; Type: DEFAULT; Schema: public; Owner: -

ALTER TABLE ONLY public.transaction_records ALTER COLUMN id SET DEFAULT nextval('public.transaction_records_id_seq'::regclass);

-- Name: user_level_progress id; Type: DEFAULT; Schema: public; Owner: -

ALTER TABLE ONLY public.user_level_progress ALTER COLUMN id SET DEFAULT nextval('public.user_level_progress_id_seq'::regclass);

-- Name: users id; Type: DEFAULT; Schema: public; Owner: -

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);

-- Name: wallets id; Type: DEFAULT; Schema: public; Owner: -

ALTER TABLE ONLY public.wallets ALTER COLUMN id SET DEFAULT nextval('public.wallets_id_seq'::regclass);

-- Name: admin_logs admin_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.admin_logs
    ADD CONSTRAINT admin_logs_pkey PRIMARY KEY (id);

-- Name: announcements announcements_pkey; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.announcements
    ADD CONSTRAINT announcements_pkey PRIMARY KEY (id);

-- Name: debt_items debt_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.debt_items
    ADD CONSTRAINT debt_items_pkey PRIMARY KEY (id);

-- Name: debt_payment_schedules debt_payment_schedules_pkey; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.debt_payment_schedules
    ADD CONSTRAINT debt_payment_schedules_pkey PRIMARY KEY (id);

-- Name: emergency_funds emergency_funds_pkey; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.emergency_funds
    ADD CONSTRAINT emergency_funds_pkey PRIMARY KEY (id);

-- Name: emergency_funds emergency_funds_user_id_key; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.emergency_funds
    ADD CONSTRAINT emergency_funds_user_id_key UNIQUE (user_id);

-- Name: fi_records fi_records_pkey; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.fi_records
    ADD CONSTRAINT fi_records_pkey PRIMARY KEY (id);

-- Name: levels levels_pkey; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.levels
    ADD CONSTRAINT levels_pkey PRIMARY KEY (id);

-- Name: transaction_records transaction_records_pkey; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.transaction_records
    ADD CONSTRAINT transaction_records_pkey PRIMARY KEY (id);

-- Name: user_level_progress user_level_progress_pkey; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.user_level_progress
    ADD CONSTRAINT user_level_progress_pkey PRIMARY KEY (id);

-- Name: user_level_progress user_level_progress_user_id_key; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.user_level_progress
    ADD CONSTRAINT user_level_progress_user_id_key UNIQUE (user_id);

-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);

-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);

-- Name: wallets wallets_pkey; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.wallets
    ADD CONSTRAINT wallets_pkey PRIMARY KEY (id);

-- Name: wallets wallets_user_id_key; Type: CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.wallets
    ADD CONSTRAINT wallets_user_id_key UNIQUE (user_id);

-- Name: admin_logs fk5xn2pg5opbtkhwxroddfqj9bt; Type: FK CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.admin_logs
    ADD CONSTRAINT fk5xn2pg5opbtkhwxroddfqj9bt FOREIGN KEY (user_id) REFERENCES public.users(id);

-- Name: debt_payment_schedules fk8ibvjabkeqgv9ehiypl7dflqw; Type: FK CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.debt_payment_schedules
    ADD CONSTRAINT fk8ibvjabkeqgv9ehiypl7dflqw FOREIGN KEY (debt_item_id) REFERENCES public.debt_items(id);

-- Name: wallets fkc1foyisidw7wqqrkamafuwn4e; Type: FK CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.wallets
    ADD CONSTRAINT fkc1foyisidw7wqqrkamafuwn4e FOREIGN KEY (user_id) REFERENCES public.users(id);

-- Name: debt_items fkcb3vv1w2gvdj3hsgjwxn4sl31; Type: FK CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.debt_items
    ADD CONSTRAINT fkcb3vv1w2gvdj3hsgjwxn4sl31 FOREIGN KEY (user_id) REFERENCES public.users(id);

-- Name: user_level_progress fkdvb9x76olan6jjf2b7nvimb1s; Type: FK CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.user_level_progress
    ADD CONSTRAINT fkdvb9x76olan6jjf2b7nvimb1s FOREIGN KEY (user_id) REFERENCES public.users(id);

-- Name: fi_records fkfr9fjr46edjsib9y7ovy3a9am; Type: FK CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.fi_records
    ADD CONSTRAINT fkfr9fjr46edjsib9y7ovy3a9am FOREIGN KEY (user_id) REFERENCES public.users(id);

-- Name: emergency_funds fkiege52qf5u79m2f4bes1kks5e; Type: FK CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.emergency_funds
    ADD CONSTRAINT fkiege52qf5u79m2f4bes1kks5e FOREIGN KEY (user_id) REFERENCES public.users(id);

-- Name: user_level_progress fkmo7o9prt4llp47800vnn292v2; Type: FK CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.user_level_progress
    ADD CONSTRAINT fkmo7o9prt4llp47800vnn292v2 FOREIGN KEY (level_id) REFERENCES public.levels(id);

-- Name: transaction_records fknxrii35nemvlgjc27yj4xukmb; Type: FK CONSTRAINT; Schema: public; Owner: -

ALTER TABLE ONLY public.transaction_records
    ADD CONSTRAINT fknxrii35nemvlgjc27yj4xukmb FOREIGN KEY (user_id) REFERENCES public.users(id);

