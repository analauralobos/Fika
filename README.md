-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-11-2023 a las 03:55:42
-- Versión del servidor: 10.1.37-MariaDB
-- Versión de PHP: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `fikadb`
--
CREATE DATABASE IF NOT EXISTS `fikadb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `fikadb`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carritos`
--

DROP TABLE IF EXISTS `carritos`;
CREATE TABLE `carritos` (
  `id` int(11) NOT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `carritos`
--

INSERT INTO `carritos` (`id`, `cliente_id`, `fecha_creacion`, `total`) VALUES
(1, 1, '2023-11-14 01:49:06', 0),
(3, 2, '2023-11-10 03:00:00', 19.49),
(4, 9, '2023-11-14 03:00:00', 6.49),
(5, 10, '2023-11-14 03:00:00', 30.06),
(6, 11, '2023-11-14 03:00:00', 72.56);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito_productos`
--

DROP TABLE IF EXISTS `carrito_productos`;
CREATE TABLE `carrito_productos` (
  `carrito_id` int(11) DEFAULT NULL,
  `producto_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `carrito_productos`
--

INSERT INTO `carrito_productos` (`carrito_id`, `producto_id`, `menu_id`) VALUES
(1, 1, NULL),
(1, NULL, 1),
(3, 7, NULL),
(3, NULL, 1),
(3, NULL, 17),
(4, NULL, 19),
(5, NULL, 18),
(6, NULL, 1),
(6, NULL, 2),
(6, NULL, 2),
(6, 3, NULL),
(6, 8, NULL),
(6, 4, NULL),
(6, 9, NULL),
(6, NULL, 18);

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
(20, 'Oferta de Mediodía 2', 'gfghhgfh', '5.98', '0.40', '0.00'),
(21, 'Promo Navidad', 'dos minitortas y dos frappes', '27.89', '0.38', '0.00');

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
(20, 4, 1),
(21, 8, 2),
(21, 9, 2);

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
(1, 'Café Espresso', 'Café', '2.50', 'https://th.bing.com/th/id/OIP.pD-sZ8PSsQW4bhYbJmkTjgHaEK?pid=ImgDet&rs=1'),
(2, 'Croissant', 'Panadería', '1.50', 'https://th.bing.com/th/id/R.b646fb97a40983872cad253a45961947?rik=8tEiNR8Zc9AOAQ&pid=ImgRaw&r=0'),
(3, 'Avocado Toast', 'Sándwich', '4.00', 'https://th.bing.com/th/id/OIP.9xQEEBknmdWBaD56b-w_KgHaHa?pid=ImgDet&rs=1'),
(4, 'Scons', 'Panadería', '3.50', 'https://th.bing.com/th/id/OIP.UgUbjNXXqmzJ0y25W8d3LgHaHa?pid=ImgDet&rs=1'),
(5, 'Cappuccino', 'Café', '7.00', 'https://th.bing.com/th/id/OIP.sORUCLQs6IFavbrcEWRPgAHaE8?pid=ImgDet&rs=1'),
(6, 'Licuado de frutilla y banana', 'Licuados', '5.00', 'https://th.bing.com/th/id/OIP.cIvYshFHyrs4MQLEcQ4cdwHaHa?pid=ImgDet&rs=1'),
(7, 'Torta de zanahoria', 'Tortas', '9.00', 'https://th.bing.com/th/id/OIP.fU-DdZ5QXGbi0dgAyxv9oAHaFR?pid=ImgDet&rs=1'),
(8, 'Mini Tarta de Chocolate y Dulce de Leche', 'Tortas', '8.00', 'https://th.bing.com/th/id/OIP.93xDnTZcAMH4XCk45zpdZAHaHa?pid=ImgDet&w=624&h=624&rs=1'),
(9, 'Frappe americana', 'Helados', '6.00', 'https://th.bing.com/th/id/OIP.81_uFFhL62B0RqxteMrTZQHaE8?pid=ImgDet&rs=1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefono` varchar(30) NOT NULL,
  `edad` int(11) NOT NULL,
  `contraseña` varchar(50) NOT NULL,
  `rol` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `email`, `telefono`, `edad`, `contraseña`, `rol`) VALUES
(1, 'Juan Pérez', 'juan@example.com', '2302445566', 30, '123456', 'CLIENTE'),
(2, 'María López', 'maria@example.com', '2302123456', 44, '123456', 'CLIENTE'),
(3, 'Francisco Villa', 'fran@example.com', '2302543210', 54, '123456', 'ADMIN'),
(9, 'Manuel Belgrano', 'manuel@example.com', '4567998877', 55, '123456', 'CLIENTE'),
(10, 'Juan Manuel de Rosas', 'rosas@example.com', '2302454545', 32, '123456', 'CLIENTE'),
(11, 'Domingo Sarmiento', 'domingo@example.com', '2302343434', 66, '123456', 'CLIENTE');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carritos`
--
ALTER TABLE `carritos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cliente_id` (`cliente_id`);

--
-- Indices de la tabla `carrito_productos`
--
ALTER TABLE `carrito_productos`
  ADD KEY `carrito_id` (`carrito_id`),
  ADD KEY `producto_id` (`producto_id`),
  ADD KEY `menu_id` (`menu_id`);

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
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carritos`
--
ALTER TABLE `carritos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `menus`
--
ALTER TABLE `menus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

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
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carritos`
--
ALTER TABLE `carritos`
  ADD CONSTRAINT `carritos_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `carrito_productos`
--
ALTER TABLE `carrito_productos`
  ADD CONSTRAINT `carrito_productos_ibfk_1` FOREIGN KEY (`carrito_id`) REFERENCES `carritos` (`id`),
  ADD CONSTRAINT `carrito_productos_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`),
  ADD CONSTRAINT `carrito_productos_ibfk_3` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`id`);

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
  ADD CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `pedidos_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
