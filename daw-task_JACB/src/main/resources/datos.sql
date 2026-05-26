INSERT INTO tarea (titulo, descripcion, fecha_creacion, fecha_vencimiento, estado) VALUES
-- Tareas Vencidas (Fechas anteriores a hoy)
('Diseñar esquema DB', 'Crear el diagrama entidad-relación inicial', '2026-03-01', '2026-03-05', 'COMPLETADA'),
('Estabilidad del PC', 'Ajustar Command Rate de RAM a 2T y voltaje a 1.350V del Ryzen 5 3600 tras pantallazos azules', '2026-01-15', '2026-01-20', 'PENDIENTE'),
('Proyecto Java con Modelio', 'Generar clases base con Java Designer y completar constructores y lógica a mano', '2026-05-01', '2026-05-20', 'EN_PROGRESO'),

-- Tareas No Vencidas (Fechas posteriores a hoy)
('Estilar vista de productos', 'Aplicar diseño CSS moderno a la lista de productos usando el control flow de Angular', '2026-05-20', '2026-06-10', 'EN_PROGRESO'),
('Optimizar JOINs en BBDD', 'Revisar las consultas SQL complejas para el esquema de la base de datos de entrenamiento', '2026-05-25', '2026-06-15', 'PENDIENTE'),
('Expandir guion de vídeo', 'Ampliar texto del script sobre SaaS y CRM para llegar a la duración objetivo de 8 minutos de locución', '2026-05-26', '2026-06-05', 'PENDIENTE'),
('Desarrollar controladores', 'Crear endpoints GET, POST, PUT y DELETE para la gestión de las tareas en Spring Boot', '2026-05-22', '2026-06-01', 'COMPLETADA'),
('Validación de DTOs', 'Añadir anotaciones para verificar que los datos entrantes son correctos en la capa web', '2026-05-26', '2026-06-14', 'PENDIENTE'),
('Pruebas en Postman', 'Testear todos los endpoints y verificar que devuelven los códigos HTTP correctos', '2026-05-26', '2026-06-02', 'PENDIENTE');