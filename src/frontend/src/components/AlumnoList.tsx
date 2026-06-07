import React, { useEffect, useState } from 'react';
import { getAlumnos, deleteAlumno } from '../services/alumno.service';
import * as gradoService from '../services/grado.service';
import type { Alumno } from '../services/alumno.service';
import { Search, Plus, Edit, Trash2, ArrowLeft, Users } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { normalizeString } from '../utils/searchUtils';
import './Listas.css';

const AlumnoList: React.FC = () => {
  const [alumnos, setAlumnos] = useState<Alumno[]>([]);
  const [grados, setGrados] = useState<any[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchAlumnos();
    gradoService.getGrados().then(res => setGrados(res.data));
  }, []);

  const fetchAlumnos = async () => {
    try {
      const response = await getAlumnos();
      setAlumnos(response.data);
      setLoading(false);
    } catch (err) {
      setError('Error al cargar los alumnos');
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('¿Estás seguro de que deseas eliminar este alumno? Esta acción no se puede deshacer.')) {
      try {
        await deleteAlumno(id);
        setAlumnos(alumnos.filter(a => a.id !== id));
      } catch (err) {
        alert('Error al eliminar el alumno.');
      }
    }
  };

  const filteredAlumnos = alumnos.filter(alumno => {
    const term = normalizeString(searchTerm);
    return normalizeString(alumno.nombre || '').includes(term) ||
           normalizeString(alumno.apellidos || '').includes(term) ||
           normalizeString(alumno.dni || '').includes(term);
  });

  if (loading) return <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-main)' }}>Cargando alumnos...</div>;
  if (error) return <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--accent)' }}>{error}</div>;

  return (
    <div className="list-container">
      <div className="list-header">
        <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
          <button 
            onClick={() => navigate('/dashboard')}
            className="btn-icon"
          >
            <ArrowLeft size={24} />
          </button>
          <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
              <Users color="var(--primary)" size={32} />
              <h2>Gestión de Alumnos</h2>
          </div>
        </div>
        <button 
          onClick={() => navigate('/alumnos/nuevo')}
          className="btn btn-primary"
          style={{ 
            display: 'flex', 
            alignItems: 'center', 
            gap: '0.5rem'
          }}
        >
          <Plus size={20} />
          <span>Añadir Alumno</span>
        </button>
      </div>

      <div className="search-filter-wrapper">
        <Search size={20} />
        <input
          type="text"
          className="search-filter-input"
          placeholder="Buscar por DNI, nombre o apellidos..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>DNI</th>
              <th>Nombre</th>
              <th>Apellidos</th>
              <th>Grado</th>
              <th style={{ textAlign: 'center' }}>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {filteredAlumnos.map((alumno) => {
              const grado = grados.find(g => g.id === alumno.gradoId);
              return (
              <tr key={alumno.id}>
                <td style={{ fontWeight: 'bold' }}>{alumno.dni}</td>
                <td>{alumno.nombre}</td>
                <td>{alumno.apellidos}</td>
                <td>{grado ? grado.titulo : 'N/A'}</td>
                <td>
                  <div className="action-btns" style={{ justifyContent: 'center' }}>
                    <button 
                      onClick={() => navigate(`/alumnos/editar/${alumno.id}`)}
                      className="btn-icon"
                      title="Editar"
                    >
                      <Edit size={18} />
                    </button>
                    <button 
                      onClick={() => handleDelete(alumno.id)}
                      className="btn-icon"
                      style={{ color: 'var(--accent)' }}
                      title="Eliminar"
                    >
                      <Trash2 size={18} />
                    </button>
                  </div>
                </td>
              </tr>
            )})}
            {filteredAlumnos.length === 0 && (
              <tr>
                <td colSpan={5} style={{ textAlign: 'center', padding: '2rem', color: 'var(--text-muted)' }}>
                  No se encontraron alumnos que coincidan con la búsqueda.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AlumnoList;
