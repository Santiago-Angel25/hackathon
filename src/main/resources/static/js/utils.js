/**
 * utils.js - Utilidades compartidas
 * Funciones reutilizables en toda la aplicación
 */

/**
 * Mostrar notificación
 * @param {string} mensaje - Mensaje a mostrar
 * @param {string} tipo - Tipo de notificación: 'success', 'error', 'warning', 'info'
 * @param {number} duracion - Duración en milisegundos (0 = no se elimina automáticamente)
 */
function mostrarNotificacion(mensaje, tipo = 'info', duracion = 5000) {
    // Crear contenedor si no existe
    let contenedor = document.getElementById('notificaciones-container');
    if (!contenedor) {
        contenedor = document.createElement('div');
        contenedor.id = 'notificaciones-container';
        contenedor.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 10000;
            display: flex;
            flex-direction: column;
            gap: 10px;
            max-width: 400px;
        `;
        document.body.appendChild(contenedor);
    }

    // Crear notificación
    const notificacion = document.createElement('div');
    const colores = {
        success: { bg: '#d4edda', border: '#c3e6cb', text: '#155724', icon: 'check-circle' },
        error: { bg: '#f8d7da', border: '#f5c6cb', text: '#721c24', icon: 'exclamation-circle' },
        warning: { bg: '#fff3cd', border: '#ffeeba', text: '#856404', icon: 'warning' },
        info: { bg: '#d1ecf1', border: '#bee5eb', text: '#0c5460', icon: 'info-circle' }
    };

    const color = colores[tipo] || colores.info;

    notificacion.style.cssText = `
        background-color: ${color.bg};
        border: 1px solid ${color.border};
        color: ${color.text};
        padding: 16px;
        border-radius: 8px;
        display: flex;
        align-items: center;
        gap: 12px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        animation: slideIn 0.3s ease;
    `;

    notificacion.innerHTML = `
        <i class="fas fa-${color.icon}" style="font-size: 20px;"></i>
        <span>${mensaje}</span>
        <button onclick="this.parentElement.remove()" style="background: none; border: none; color: ${color.text}; cursor: pointer; font-size: 20px; margin-left: auto;">
            <i class="fas fa-times"></i>
        </button>
    `;

    contenedor.appendChild(notificacion);

    if (duracion > 0) {
        setTimeout(() => {
            notificacion.style.animation = 'slideOut 0.3s ease';
            setTimeout(() => notificacion.remove(), 300);
        }, duracion);
    }
}

/**
 * Mostrar modal de confirmación
 * @param {string} titulo - Título del modal
 * @param {string} mensaje - Mensaje de confirmación
 * @param {function} callback - Función a ejecutar si se confirma
 * @param {string} btnText - Texto del botón de confirmación
 */
function confirmar(titulo, mensaje, callback, btnText = 'Confirmar') {
    // Crear modal
    const modal = document.createElement('div');
    modal.style.cssText = `
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 9999;
        animation: fadeIn 0.3s ease;
    `;

    modal.innerHTML = `
        <div style="
            background: white;
            border-radius: 12px;
            padding: 32px;
            max-width: 400px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
            animation: slideUp 0.3s ease;
        ">
            <h2 style="color: #2c3e50; margin-bottom: 12px; font-size: 24px;">
                <i class="fas fa-question-circle" style="color: #f39c12; margin-right: 8px;"></i>${titulo}
            </h2>
            <p style="color: #666; margin-bottom: 24px;">${mensaje}</p>
            <div style="display: flex; gap: 12px; justify-content: flex-end;">
                <button onclick="this.closest('div').parentElement.remove()" style="
                    background-color: #e0e0e0;
                    color: #333;
                    border: none;
                    padding: 10px 20px;
                    border-radius: 6px;
                    cursor: pointer;
                    font-weight: 600;
                    transition: all 0.3s ease;
                ">
                    Cancelar
                </button>
                <button onclick="this.closest('div').parentElement.remove()" id="btn-confirmar" style="
                    background-color: #2ecc71;
                    color: white;
                    border: none;
                    padding: 10px 20px;
                    border-radius: 6px;
                    cursor: pointer;
                    font-weight: 600;
                    transition: all 0.3s ease;
                ">
                    ${btnText}
                </button>
            </div>
        </div>
    `;

    document.body.appendChild(modal);

    document.getElementById('btn-confirmar').addEventListener('click', () => {
        callback();
        modal.remove();
    });
}

/**
 * Formatear fecha
 * @param {string|Date} fecha - Fecha a formatear
 * @param {string} formato - Formato deseado (por defecto: 'DD/MM/YYYY')
 * @returns {string}
 */
function formatearFecha(fecha, formato = 'DD/MM/YYYY') {
    if (!fecha) return '';

    const date = new Date(fecha);
    const dia = String(date.getDate()).padStart(2, '0');
    const mes = String(date.getMonth() + 1).padStart(2, '0');
    const año = date.getFullYear();
    const hora = String(date.getHours()).padStart(2, '0');
    const minuto = String(date.getMinutes()).padStart(2, '0');

    const formatos = {
        'DD/MM/YYYY': `${dia}/${mes}/${año}`,
        'YYYY-MM-DD': `${año}-${mes}-${dia}`,
        'DD/MM/YYYY HH:MM': `${dia}/${mes}/${año} ${hora}:${minuto}`,
        'YYYY-MM-DD HH:MM': `${año}-${mes}-${dia} ${hora}:${minuto}`,
        'HH:MM': `${hora}:${minuto}`,
        'DD MMM': `${dia} ${['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'][date.getMonth()]}`
    };

    return formatos[formato] || formatos['DD/MM/YYYY'];
}

/**
 * Calcular tiempo transcurrido (ej: "hace 2 horas")
 * @param {string|Date} fecha - Fecha a calcular
 * @returns {string}
 */
function tiempoTranscurrido(fecha) {
    if (!fecha) return '';

    const ahora = new Date();
    const fechaObj = new Date(fecha);
    const diferencia = ahora - fechaObj;

    const segundos = Math.floor(diferencia / 1000);
    const minutos = Math.floor(segundos / 60);
    const horas = Math.floor(minutos / 60);
    const dias = Math.floor(horas / 24);

    if (segundos < 60) return 'Hace unos segundos';
    if (minutos < 60) return `Hace ${minutos} ${minutos === 1 ? 'minuto' : 'minutos'}`;
    if (horas < 24) return `Hace ${horas} ${horas === 1 ? 'hora' : 'horas'}`;
    if (dias < 7) return `Hace ${dias} ${dias === 1 ? 'día' : 'días'}`;
    if (dias < 30) return `Hace ${Math.floor(dias / 7)} semanas`;

    return formatearFecha(fecha);
}

/**
 * Validar email
 * @param {string} email - Email a validar
 * @returns {boolean}
 */
function validarEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

/**
 * Validar teléfono
 * @param {string} telefono - Teléfono a validar
 * @returns {boolean}
 */
function validarTelefono(telefono) {
    const regex = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/;
    return regex.test(telefono.replace(/\s/g, ''));
}

/**
 * Sanitizar HTML (prevenir XSS)
 * @param {string} texto - Texto a sanitizar
 * @returns {string}
 */
function sanitizar(texto) {
    const div = document.createElement('div');
    div.textContent = texto;
    return div.innerHTML;
}

/**
 * Copiar texto al portapapeles
 * @param {string} texto - Texto a copiar
 */
function copiarAlPortapapeles(texto) {
    navigator.clipboard.writeText(texto).then(() => {
        mostrarNotificacion('Copiado al portapapeles', 'success', 2000);
    }).catch(() => {
        mostrarNotificacion('Error al copiar', 'error');
    });
}

/**
 * Descargar archivo
 * @param {string} contenido - Contenido del archivo
 * @param {string} nombreArchivo - Nombre del archivo
 * @param {string} tipo - Tipo MIME
 */
function descargarArchivo(contenido, nombreArchivo, tipo = 'text/plain') {
    const elemento = document.createElement('a');
    elemento.setAttribute('href', `data:${tipo};charset=utf-8,${encodeURIComponent(contenido)}`);
    elemento.setAttribute('download', nombreArchivo);
    elemento.style.display = 'none';
    document.body.appendChild(elemento);
    elemento.click();
    document.body.removeChild(elemento);
}

/**
 * Obtener color por estado de donación
 * @param {string} estado - Estado de la donación
 * @returns {string} Código de color
 */
function getColorPorEstado(estado) {
    const colores = {
        'DISPONIBLE': '#2ecc71',
        'RESERVADO': '#f39c12',
        'ENTREGADO': '#27ae60',
        'CANCELADO': '#e74c3c'
    };
    return colores[estado] || '#95a5a6';
}

/**
 * Obtener icono por categoría
 * @param {string} categoria - Categoría de donación
 * @returns {string} Icono Font Awesome
 */
function getIconoPorCategoria(categoria) {
    const iconos = {
        'comida': 'fa-utensils',
        'ropa': 'fa-shirt',
        'libros': 'fa-book',
        'electrónica': 'fa-laptop',
        'muebles': 'fa-couch',
        'juguetes': 'fa-gamepad',
        'otros': 'fa-box'
    };
    return iconos[categoria?.toLowerCase()] || 'fa-box';
}

/**
 * Obtener color por categoría
 * @param {string} categoria - Categoría de donación
 * @returns {string} Código de color
 */
function getColorPorCategoria(categoria) {
    const colores = {
        'comida': '#e74c3c',
        'ropa': '#3498db',
        'libros': '#9b59b6',
        'electrónica': '#1abc9c',
        'muebles': '#e67e22',
        'juguetes': '#ec407a',
        'otros': '#95a5a6'
    };
    return colores[categoria?.toLowerCase()] || '#2ecc71';
}

/**
 * Generar UUID
 * @returns {string}
 */
function generarUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        const r = Math.random() * 16 | 0;
        const v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

/**
 * Verificar si el dispositivo es móvil
 * @returns {boolean}
 */
function esMobile() {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
}

/**
 * Esperar ms milisegundos (útil con async/await)
 * @param {number} ms - Milisegundos a esperar
 * @returns {Promise}
 */
function esperar(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

/**
 * Agregar estilos de animación al documento
 */
if (!document.getElementById('animations-css')) {
    const style = document.createElement('style');
    style.id = 'animations-css';
    style.textContent = `
        @keyframes slideIn {
            from {
                transform: translateX(400px);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }

        @keyframes slideOut {
            from {
                transform: translateX(0);
                opacity: 1;
            }
            to {
                transform: translateX(400px);
                opacity: 0;
            }
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    `;
    document.head.appendChild(style);
}

