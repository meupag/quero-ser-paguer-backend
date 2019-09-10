
create database pag;

CREATE TABLE public.cliente (
    id bigint NOT NULL,
    nome character varying(100) NOT NULL,
    cpf character(11) NOT NULL,
    data_nascimento date
);


ALTER TABLE public.cliente OWNER TO postgres;


CREATE SEQUENCE public.cliente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cliente_id_seq OWNER TO postgres;

ALTER SEQUENCE public.cliente_id_seq OWNED BY public.cliente.id;

CREATE TABLE public.item_pedido (
    id bigint NOT NULL,
    id_pedido bigint NOT NULL,
    id_produto bigint NOT NULL,
    quantidade integer NOT NULL,
    preco double precision NOT NULL
);


ALTER TABLE public.item_pedido OWNER TO postgres;

CREATE SEQUENCE public.item_pedido_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.item_pedido_id_seq OWNER TO postgres;

ALTER SEQUENCE public.item_pedido_id_seq OWNED BY public.item_pedido.id;

CREATE TABLE public.pedido (
    id bigint NOT NULL,
    id_cliente bigint NOT NULL,
    valor double precision NOT NULL
);

ALTER TABLE public.pedido OWNER TO postgres;

CREATE SEQUENCE public.pedido_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.pedido_id_seq OWNER TO postgres;

ALTER SEQUENCE public.pedido_id_seq OWNED BY public.pedido.id;

CREATE TABLE public.produto (
    id bigint NOT NULL,
    nome character varying(100) NOT NULL,
    preco_sugerido double precision
);

ALTER TABLE public.produto OWNER TO postgres;

CREATE SEQUENCE public.produto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.produto_id_seq OWNER TO postgres;

ALTER SEQUENCE public.produto_id_seq OWNED BY public.produto.id;

ALTER TABLE ONLY public.cliente ALTER COLUMN id SET DEFAULT nextval('public.cliente_id_seq'::regclass);

ALTER TABLE ONLY public.item_pedido ALTER COLUMN id SET DEFAULT nextval('public.item_pedido_id_seq'::regclass);

ALTER TABLE ONLY public.pedido ALTER COLUMN id SET DEFAULT nextval('public.pedido_id_seq'::regclass);

ALTER TABLE ONLY public.produto ALTER COLUMN id SET DEFAULT nextval('public.produto_id_seq'::regclass);

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.item_pedido
    ADD CONSTRAINT item_pedido_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.item_pedido
    ADD CONSTRAINT item_pedido_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id);

ALTER TABLE ONLY public.item_pedido
    ADD CONSTRAINT item_pedido_id_produto_fkey FOREIGN KEY (id_produto) REFERENCES public.produto(id);

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_id_cliente_fkey FOREIGN KEY (id_cliente) REFERENCES public.cliente(id);
