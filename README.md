-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-11-2023 a las 09:49:11
-- Versión del servidor: 10.1.37-MariaDB
-- Versión de PHP: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de datos: `fikadb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

DROP TABLE IF EXISTS `clientes`;
CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefono` varchar(30) NOT NULL,
  `edad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `nombre`, `email`, `telefono`, `edad`) VALUES
(1, 'Juan Pérez', 'juan@example.com', '2302445566', 30),
(2, 'María López', 'maria@example.com', '2302123456', 44);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `menus`
--

DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `descripcion` text,
  `precio_total` decimal(10,2) NOT NULL,
  `descuento` decimal(10,2) DEFAULT NULL,
  `aumento` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `menus`
--

INSERT INTO `menus` (`id`, `nombre`, `descripcion`, `precio_total`, `descuento`, `aumento`) VALUES
(1, 'Menú de Desayuno', 'Desayuno con café y croissant', '6.00', NULL, '0.50'),
(2, 'Menú de Almuerzo', 'Almuerzo con sándwich y ensalada', '7.50', '1.00', NULL),
(17, 'Menu Merienda', '1 licuado de frutilla y dos croissants', '6.49', '0.15', NULL),
(18, 'Menu Cena con Postre', '2 cafés espresso, 2 licuados de frutilla y banana, 2 sándwiches de pollo y 2 ensaladas cesar', '30.06', '0.00', '0.20'),
(19, 'Oferta de Mediodía', '1 café espresso y un sándwich de pollo', '6.49', NULL, NULL),
(20, 'Oferta de Mediodía 2', 'gfghhgfh', '5.98', '0.40', '0.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `menu_producto`
--

DROP TABLE IF EXISTS `menu_producto`;
CREATE TABLE `menu_producto` (
  `menu_id` int(11) NOT NULL,
  `producto_id` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `menu_producto`
--

INSERT INTO `menu_producto` (`menu_id`, `producto_id`, `cantidad`) VALUES
(1, 1, 1),
(1, 2, 2),
(2, 3, 1),
(2, 4, 1),
(17, 2, 2),
(17, 6, 1),
(18, 1, 2),
(18, 3, 2),
(18, 4, 2),
(18, 6, 2),
(19, 1, 1),
(19, 3, 1),
(20, 1, 1),
(20, 4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
CREATE TABLE `pedidos` (
  `id` int(11) NOT NULL,
  `fecha_pedido` date DEFAULT NULL,
  `fecha_entrega` date DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`id`, `fecha_pedido`, `fecha_entrega`, `estado`, `cliente_id`, `menu_id`) VALUES
(4, '2023-11-06', '2023-11-08', 'Pendiente', 1, 17),
(5, '2023-11-01', '2023-11-02', 'Entregado', 2, 2),
(6, '2023-11-03', '2023-11-03', 'En Proceso', 1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

DROP TABLE IF EXISTS `productos`;
CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `precio` decimal(10,2) NOT NULL,
  `imagenURL` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `nombre`, `categoria`, `precio`, `imagenURL`) VALUES
(1, 'Café Espresso', 'Café', '2.50', 'https://scontent.fgpo2-1.fna.fbcdn.net/v/t39.30808-6/333447928_192830956724355_1649625247474566647_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=5f2048&_nc_ohc=s0Lw99Gaa5AAX_pA0kd&_nc_ht=scontent.fgpo2-1.fna&oh=00_AfCXcGtj8XysCW1jv26hiPvzCqupMNfoZeOGXHunyuV_kA&oe=655058CB'),
(2, 'Croissant', 'Panadería', '1.50', 'https://scontent.fgpo2-1.fna.fbcdn.net/v/t39.30808-6/344753720_663532498916934_1140539468550432441_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_ohc=Pztj-86W8mgAX_AoRLS&_nc_ht=scontent.fgpo2-1.fna&oh=00_AfDOD52JbJ6BSdqH_Ij_pIgV_u32Dd0D2kUfShJT6DxJIA&oe=6550B023'),
(3, 'Avocado Toast', 'Sándwich', '4.00', 'https://scontent.fgpo2-1.fna.fbcdn.net/v/t39.30808-6/291892065_375594854681392_763866727399824180_n.jpg?_nc_cat=110&ccb=1-7&_nc_sid=5f2048&_nc_ohc=LIcJ20AIKfUAX9qj2a4&_nc_ht=scontent.fgpo2-1.fna&oh=00_AfDnO1SJRRMzGv5nqydNaKa2mAVK8n490CJkaIWVPceUFA&oe=65505E02'),
(4, 'Scons', 'Panadería', '3.50', 'https://scontent.fgpo2-1.fna.fbcdn.net/v/t39.30808-6/365184849_622220236681466_8350090581753312053_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=5f2048&_nc_ohc=u9Tj6CkQetEAX_jR-7N&_nc_ht=scontent.fgpo2-1.fna&oh=00_AfAk-JctIMmocOFXO2xOaZDc8HUtSeA7AVVgGeV38eUmxg&oe=65500D9E'),
(5, 'Cappuccino', 'Café', '7.00', 'https://scontent.fgpo2-1.fna.fbcdn.net/v/t39.30808-6/299750310_401681455406065_2172305888026799752_n.jpg?_nc_cat=110&ccb=1-7&_nc_sid=5f2048&_nc_ohc=lduaxwzRrWwAX8B6SvI&_nc_ht=scontent.fgpo2-1.fna&oh=00_AfAn25wCqzWKsMIRJ_vsb8Wiw5XzMidTH71aN4yKB4LD7g&oe=654F9FC4'),
(6, 'Licuado de frutilla y banana', 'Licuados', '5.00', 'https://scontent.fgpo2-1.fna.fbcdn.net/v/t39.30808-6/305037413_415356167371927_2397485448618020930_n.jpg?_nc_cat=110&ccb=1-7&_nc_sid=5f2048&_nc_ohc=LWXJeM4YzjMAX-gsolk&_nc_ht=scontent.fgpo2-1.fna&oh=00_AfBNuKn6QI6G9Nz6qwyR9GQAXJVepocQ8Fc5m2FJIEVwOg&oe=65500761'),
(7, 'Torta de zanahoria', 'Tortas', '9.00', 'https://scontent.fgpo2-1.fna.fbcdn.net/v/t39.30808-6/383210431_646586400911516_1908898908354379200_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_ohc=OLuzrVeTGqcAX8BzHa8&_nc_ht=scontent.fgpo2-1.fna&oh=00_AfBugAzc1VLPf3A62oeHI8fJ5I8-B_3Z1cQFix70IBFJ8w&oe=6550BD57'),
(8, 'Mini torta de chocolate y dulce de leche', 'Tortas', '8.00', 'https://scontent.fgpo2-1.fna.fbcdn.net/v/t39.30808-6/365771078_622218156681674_5688235395764110437_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=5f2048&_nc_ohc=Nmn0jhGuyaAAX_mLlPw&_nc_ht=scontent.fgpo2-1.fna&oh=00_AfBdVVu-N-r5AMdR72FBvo1IKc84y6QCB0D34RK9iTDdMg&oe=654FBC50'),
(9, 'Frappe americana', 'Helados', '6.00', 'https://scontent.fgpo2-1.fna.fbcdn.net/v/t39.30808-6/326199269_1343006366458547_3804320178252054790_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_ohc=ASU3B9t2HWUAX_z3Hw0&_nc_ht=scontent.fgpo2-1.fna&oh=00_AfCh_LgaCZkAdUHOVBKrYkrIu7bU1Dq2QBSn-Fga7DQV4w&oe=654F5189');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `menus`
--
ALTER TABLE `menus`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `menu_producto`
--
ALTER TABLE `menu_producto`
  ADD KEY `menu_id` (`menu_id`),
  ADD KEY `producto_id` (`producto_id`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cliente_id` (`cliente_id`),
  ADD KEY `menu_id` (`menu_id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `menus`
--
ALTER TABLE `menus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `menu_producto`
--
ALTER TABLE `menu_producto`
  ADD CONSTRAINT `menu_producto_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`id`),
  ADD CONSTRAINT `menu_producto_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`);

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`),
  ADD CONSTRAINT `pedidos_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`id`);
COMMIT;

