create table code
(
  code                  text primary key,
  source                text not null,
  code_list_code        text not null,
  display_value         text not null,
  long_description      text default '',
  from_date             date not null,
  to_date               date,
  sorting_priority      int
);