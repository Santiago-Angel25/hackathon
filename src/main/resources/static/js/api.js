/**
 * api.js - Gestión centralizada de llamadas a la API
 * Este archivo centraliza todas las comunicaciones con el backend
 */

const API_BASE_URL = '/api';

/**
 * Realizar una llamada fetch genérica con manejo de errores
 * @param {string} endpoint - Ruta del endpoint
 * @param {object} options - Opciones adicionales (method, body, etc)
 * @returns {Promise} Respuesta JSON
 */
async function apiCall(endpoint, options = {}) {
    const url = `${API_BASE_URL}${endpoint}`;
    const token = localStorage.getItem('token');

    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    try {
        const response = await fetch(url, {
            ...options,
            headers
        });

        // Si es 401, el token expiró
        if (response.status === 401) {
            localStorage.removeItem('token');
            localStorage.removeItem('rol');
            window.location.href = '/login';
            return null;
        }

        // Si es 403, acceso denegado
        if (response.status === 403) {
            window.location.href = '/acceso-denegado';
            return null;
        }

        if (!response.ok) {
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }

        return await response.json();
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

/**
 * ==================== ENDPOINTS DE AUTENTICACIÓN ====================
 */

async function registrar(usuario) {
    return apiCall('/auth/registro', {
        method: 'POST',
        body: JSON.stringify(usuario)
    });
}

async function login(email, password) {
    return apiCall('/auth/login', {
        method: 'POST',
        body: JSON.stringify({ email, password })
    });
}

async function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('rol');
    localStorage.removeItem('usuario');
    window.location.href = '/';
}

async function verificarToken() {
    return apiCall('/auth/verify', {
        method: 'GET'
    });
}

/**
 * ==================== ENDPOINTS DE DONACIONES ====================
 */

async function getDonaciones() {
    return apiCall('/donaciones', {
        method: 'GET'
    });
}

async function getDonacion(id) {
    return apiCall(`/donaciones/${id}`, {
        method: 'GET'
    });
}

async function crearDonacion(donacion) {
    return apiCall('/donaciones', {
        method: 'POST',
        body: JSON.stringify(donacion)
    });
}

async function actualizarDonacion(id, donacion) {
    return apiCall(`/donaciones/${id}`, {
        method: 'PUT',
        body: JSON.stringify(donacion)
    });
}

async function eliminarDonacion(id) {
    return apiCall(`/donaciones/${id}`, {
        method: 'DELETE'
    });
}

async function cambiarEstadoDonacion(id, estado) {
    return apiCall(`/donaciones/${id}/estado`, {
        method: 'PATCH',
        body: JSON.stringify({ estado })
    });
}

async function getDonacionesPorUsuario(usuarioId) {
    return apiCall(`/donaciones/usuario/${usuarioId}`, {
        method: 'GET'
    });
}

/**
 * ==================== ENDPOINTS DE USUARIOS ====================
 */

async function getUsuario(id) {
    return apiCall(`/usuarios/${id}`, {
        method: 'GET'
    });
}

async function actualizarUsuario(id, usuario) {
    return apiCall(`/usuarios/${id}`, {
        method: 'PUT',
        body: JSON.stringify(usuario)
    });
}

async function getUsuarios() {
    return apiCall('/usuarios', {
        method: 'GET'
    });
}

async function cambiarEstadoUsuario(id, activo) {
    return apiCall(`/usuarios/${id}/estado`, {
        method: 'PATCH',
        body: JSON.stringify({ activo })
    });
}

/**
 * ==================== ENDPOINTS DE SOLICITUDES ====================
 */

async function crearSolicitud(solicitud) {
    return apiCall('/solicitudes', {
        method: 'POST',
        body: JSON.stringify(solicitud)
    });
}

async function getSolicitudes(usuarioId) {
    return apiCall(`/solicitudes/usuario/${usuarioId}`, {
        method: 'GET'
    });
}

async function getSolicitud(id) {
    return apiCall(`/solicitudes/${id}`, {
        method: 'GET'
    });
}

async function actualizarSolicitud(id, solicitud) {
    return apiCall(`/solicitudes/${id}`, {
        method: 'PUT',
        body: JSON.stringify(solicitud)
    });
}

async function cambiarEstadoSolicitud(id, estado) {
    return apiCall(`/solicitudes/${id}/estado`, {
        method: 'PATCH',
        body: JSON.stringify({ estado })
    });
}

/**
 * ==================== ENDPOINTS DE NOTIFICACIONES ====================
 */

async function getNotificaciones(usuarioId) {
    return apiCall(`/notificaciones/usuario/${usuarioId}`, {
        method: 'GET'
    });
}

async function marcarNotificacionComoLeida(id) {
    return apiCall(`/notificaciones/${id}/leer`, {
        method: 'PATCH'
    });
}

async function marcarTodasComoLeidas() {
    return apiCall('/notificaciones/marcar-todas', {
        method: 'PATCH'
    });
}

/**
 * ==================== ENDPOINTS DE ADMIN ====================
 */

async function getDonacionesPendientes() {
    return apiCall('/admin/donaciones/pendientes', {
        method: 'GET'
    });
}

async function aprobarDonacion(id) {
    return apiCall(`/admin/donaciones/${id}/aprobar`, {
        method: 'PATCH'
    });
}

async function rechazarDonacion(id, razon) {
    return apiCall(`/admin/donaciones/${id}/rechazar`, {
        method: 'PATCH',
        body: JSON.stringify({ razon })
    });
}

async function getEstadisticas() {
    return apiCall('/admin/estadisticas', {
        method: 'GET'
    });
}

/**
 * ==================== UTILIDADES ====================
 */

/**
 * Verificar si el usuario está autenticado
 * @returns {boolean}
 */
function estaAutenticado() {
    return !!localStorage.getItem('token');
}

/**
 * Obtener el rol del usuario actual
 * @returns {string|null}
 */
function obtenerRol() {
    return localStorage.getItem('rol');
}

/**
 * Verificar si el usuario tiene un rol específico
 * @param {string|array} rolesRequeridos - Rol o array de roles
 * @returns {boolean}
 */
function tieneRol(rolesRequeridos) {
    if (!estaAutenticado()) return false;

    const rol = obtenerRol();

    if (Array.isArray(rolesRequeridos)) {
        return rolesRequeridos.includes(rol);
    }

    return rol === rolesRequeridos;
}

/**
 * Redirigir a login si no está autenticado
 */
function protegerRuta() {
    if (!estaAutenticado()) {
        window.location.href = '/login';
    }
}

/**
 * Redirigir si no tiene el rol requerido
 * @param {string|array} rolesRequeridos
 */
function protegerRuta(rolesRequeridos) {
    if (!estaAutenticado()) {
        window.location.href = '/login';
        return;
    }

    if (!tieneRol(rolesRequeridos)) {
        window.location.href = '/acceso-denegado';
    }
}

