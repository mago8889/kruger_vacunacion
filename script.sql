INSERT INTO tbl_rol(
	id_rol, nombre)
	VALUES (nextval('seqrol'), 'Administrador');

INSERT INTO tbl_rol(
	id_rol, nombre)
	VALUES (nextval('seqrol'), 'Empleado');

INSERT INTO tbl_empleados(
	id_empleado, apellidos, cedula, celular, correo, direccion, 
	fecha_nacimiento, nombres, clave, estado)
	VALUES (nextval('seqempleado'),'Administrador', 123456789, 000000, 'admin@admin.com', 'direccion',
			current_date, 'Administrador','$2a$10$6uys5QJnRhe.zYpLnvL7UubKxCBwG6A86a2./e8YNzkHZouCXuyD6','Y'); --1MReq

INSERT INTO tbl_empleados(
	id_empleado, apellidos, cedula, celular, correo, direccion,
	fecha_nacimiento, nombres, clave, estado)
	VALUES (nextval('seqempleado'),'Empleado', 987654321, 000000, 'emple@empleado.com', 'direccion', 
			current_date, 'Empleado','$2a$10$.cGIUz9NIMr7nGcW7/vYT.37Lp46AXHdyOu.wwEX9hkDD9tnj/GF2','Y'); --m3wO6

INSERT INTO tbl_user_rol(
	id_empleado, id_rol)
	VALUES (1, 1);

INSERT INTO tbl_user_rol(
	id_empleado, id_rol)
	VALUES (2, 2);

INSERT INTO public.tbl_tipovacuna(
	id_tipo_vacuna, nombre)
	VALUES (nextval('seqtipovacuna'), 'Sputnik');

INSERT INTO public.tbl_tipovacuna(
	id_tipo_vacuna, nombre)
	VALUES (nextval('seqtipovacuna'), 'AstraZeneca');
	
INSERT INTO public.tbl_tipovacuna(
	id_tipo_vacuna, nombre)
	VALUES (nextval('seqtipovacuna'), 'Pfizer');
	
INSERT INTO public.tbl_tipovacuna(
	id_tipo_vacuna, nombre)
	VALUES (nextval('seqtipovacuna'), 'Jhonson&Jhonson');
