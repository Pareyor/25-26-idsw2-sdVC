import React, { useEffect, useState } from 'react';
import { getAsignaturas, deleteAsignatura } from '../services/asignatura.service';
import type { Asignatura } from '../services/asignatura.service';
import { Search, Plus, Edit, Trash2, ArrowLeft, BookOpen } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { normalizeString } from '../utils/searchUtils';
import './Listas.css';

const AsignaturaList: React.FC = () => {
  const [asignaturas, setAsignaturas] = useState<Asignatura[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchAsignaturas();
  }, []);

  const fetchAsignaturas = async () => {
    console.log("DEBUG - Intentando cargar asignaturas...");
    try {
      const response = await getAsignaturas();
      console.log("DEBUG - Asignaturas recibidas:", response.data);
      setAsignaturas(response.data);
      setLoading(false);
    } catch (err) {
      console.error("DEBUG - Error al cargar:", err);
      setError('Error al cargar las asignaturas');
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('¿Estás seguro de que deseas eliminar esta asignatura? Esta acción no se puede deshacer.')) {
      try {
        await deleteAsignatura(id);
        setAsignaturas(asignaturas.filter(a => a.id !== id));
      } catch (err) {
        alert('Error al eliminar la asignatura. Es posible que tenga alumnos o preguntas vinculadas.');
      }
    }
  };

  const filteredAsignaturas = asignaturas.filter(asignatura => {
    const term = normalizeString(searchTerm);
    return normalizeString(asignatura.titulo).includes(term) ||
           normalizeString(asignatura.codigo).includes(term);
  });

  if (loading) return <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-main)' }}>Cargando asignaturas...</div>;
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
              <BookOpen color="var(--primary)" size={32} />
              <h2>Gestión de Asignaturas</h2>
          </div>
        </div>
        <button 
          onClick={() => navigate('/asignaturas/nuevo')}
          className="btn btn-primary"
          style={{ 
            display: 'flex', 
            alignItems: 'center', 
            gap: '0.5rem'
          }}
        >
          <Plus size={20} />
          <span>Añadir Asignatura</span>
        </button>
      </div>

      <div className="search-filter-wrapper">
        <Search size={20} />
        <input
          type="text"
          className="search-filter-input"
          placeholder="Buscar por código o título..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Código</th>
              <th>Título</th>
              <th>Curso</th>
              <th style={{ textAlign: 'center' }}>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {filteredAsignaturas.map((asignatura) => (
              <tr key={asignatura.id}>
                <td style={{ fontWeight: 'bold' }}>{asignatura.codigo}</td>
                <td>{asignatura.titulo}</td>
                <td>{asignatura.cursoAcademico}</td>
                <td>
                  <div className="action-btns" style={{ justifyContent: 'center' }}>
                    <button 
                      onClick={() => navigate(`/asignaturas/editar/${asignatura.id}`)}
                      className="btn-icon"
                      title="Editar"
                    >
                      <Edit size={18} />
                    </button>
                    <button 
                      onClick={() => handleDelete(asignatura.id)}
                      className="btn-icon"
                      style={{ color: 'var(--accent)' }}
                      title="Eliminar"
                    >
                      <Trash2 size={18} />
                    </button>
                  </div>
                </td>
              </tr>
            ))}
            {filteredAsignaturas.length === 0 && (
              <tr>
                <td colSpan={4} style={{ textAlign: 'center', padding: '2rem', color: 'var(--text-muted)' }}>
                  No se encontraron asignaturas que coincidan con la búsqueda.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AsignaturaList;
