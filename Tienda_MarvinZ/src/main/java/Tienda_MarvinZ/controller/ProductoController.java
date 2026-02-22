/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tienda_MarvinZ.controller;

import Tienda_MarvinZ.domain.Producto;
import Tienda_MarvinZ.service.ProductoService;
import Tienda_MarvinZ.service.CategoriaService;

import jakarta.validation.Valid;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private ProductoService productoService;

   @Autowired
private CategoriaService categoriaService;

   @GetMapping("/listado")
public String inicio(Model model) {
    var productos = productoService.getProductos(true);
    var categorias = categoriaService.getCategorias(true);

    model.addAttribute("productos", productos);
    model.addAttribute("totalProductos", productos.size());
    model.addAttribute("categorias", categorias);   // 👈 faltaba
    model.addAttribute("producto", new Producto()); // 👈 faltaba

    return "producto/listado";
}

 
@PostMapping("/guardar")
public String guardar(@Valid Producto producto, @RequestParam MultipartFile imagenFile, RedirectAttributes redirectAttributes) {
    
    productoService.save(producto, imagenFile);
    redirectAttributes.addFlashAttribute("todoOk", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
    
    return "redirect:/producto/listado";
}

@PostMapping("/eliminar")
public String eliminar(@RequestParam Integer idProducto, RedirectAttributes redirectAttributes) {
    String titulo="todoOk";
    String detalle="mensaje.eliminado";
    try {
        productoService.delete(idProducto);
    } catch (IllegalArgumentException e) {
        titulo="error"; // Captura la excepción de argumento inválido para el mensaje de "no existe"
        detalle="cateogira.error01";
    } catch (IllegalStateException e) {
        titulo="error"; // Captura la excepción de estado ilegal para el mensaje de "datos asociados"
        detalle="cateogira.error02";
    } catch (Exception e) {
        titulo="error"; // Captura cualquier otra excepción inesperada
        detalle="cateogira.error03";
    }
    redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(detalle, null, Locale.getDefault()));
    return "redirect:/producto/listado";
}

@GetMapping("/modificar/{idProducto}")
public String modificar(@PathVariable("idProducto") Integer idProducto, Model model, RedirectAttributes redirectAttributes) {
    Optional<Producto> productoOpt = productoService.getProducto(idProducto);
    if (productoOpt.isEmpty()) {
        redirectAttributes.addFlashAttribute("error", messageSource.getMessage("producto.error01", null, Locale.getDefault()));
        return "redirect:/producto/listado";
    }
    
    model.addAttribute("producto", productoOpt.get());
    var categorias = categoriaService.getCategorias(true);
model.addAttribute("categorias", categorias);
    return "/producto/modifica";
}
}

