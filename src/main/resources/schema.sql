create table pizza(
  id bigint auto_increment,
  nome varchar(100) not null,
  descrizione varchar(255) not null
);

create table aggiunta(
  id bigint auto_increment,
  nome varchar(100) not null,
  descrizione varchar(255) not null
);

create table ordine(
  id bigint auto_increment,
  stato varchar(10) not null,
  cliente varchar(100) not null
);

create table ordine_pizza(
  id bigint auto_increment,
  ordine_id bigint not null,
  pizza_id bigint not null,
  foreign key (ordine_id) references ordine(id),
  foreign key (pizza_id) references pizza(id)
);

create table ordine_pizza_aggiunta(
  ordine_pizza_id bigint not null,
  aggiunta_id bigint not null,
  foreign key (ordine_pizza_id) references ordine_pizza(id),
  foreign key (aggiunta_id) references aggiunta(id)
);