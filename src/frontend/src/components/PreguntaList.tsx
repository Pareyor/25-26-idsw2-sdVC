import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { getPreguntas, getPreguntasByAsignatura, deletePregunta } from '../services/pregunta.service';
import type { Pregunta } from '../types/pregunta';
import { Search, Plus, Edit, Trash2, ArrowLeft, HelpCircle, Filter } from 'lucide-react';
import { normalizeString } from '../utils/searchUtils';
import './Listas.css';

const PreguntaList: React.FC = () => {
  const [preguntas, setPreguntas] = useState<Pregunta[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const location = useLocation();
  const state = location.state as { asignaturaId?: number };
  const filteredAsignaturaId = state?.asignaturaId;

  useEffect(() => {
    fetchPreguntas();
  }, [filteredAsignaturaId]);

  const fetchPreguntas = async () => {
    try {
      setLoading(true);
      const response = filteredAsignaturaId 
        ? await getPreguntasByAsignatura(filteredAsignaturaId)
        : await getPreguntas();
      setPreguntas(response.data);
      setLoading(false);
    } catch (err) {
      setError('Error al cargar las preguntas');
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('¿Estás seguro de que deseas eliminar esta pregunta? Esto eliminará también todas sus respuestas asociadas.')) {
      try {
        await deletePregunta(id);
        setPreguntas(preguntas.filter(p => p.id !== id));
      } catch (err) {
        alert('Error al eliminar la pregunta.');
      }
    }
  };

  const filteredPreguntas = preguntas.filter(pregunta => {
    const term = normalizeString(searchTerm);
    return normalizeString(pregunta.enunciado || '').includes(term) ||
           normalizeString(pregunta.tema || '').includes(term) ||
           normalizeString(pregunta.dificultad || '').includes(term);
  });

  const getDificultadClass = (dificultad: string) => {
    switch (dificultad) {
      case 'FACIL': return 'badge-success';
      case 'MEDIO': return 'badge-warning';
      case 'DIFICIL': return 'badge-danger';
      default: return 'badge-info';
    }
  };

  if (loading) return <div className="loading-state">Cargando preguntas...</div>;
  if (error) return <div className="error-message">{error}</div>;

  return (
    <div className="list-container">
      <div className="list-header">
        <div className="flex-row-center gap-4">
          <button 
            onClick={() => filteredAsignaturaId ? navigate(`/asignaturas/editar/${filteredAsignaturaId}`) : navigate('/dashboard')}
            className="btn-icon"
            title="Volver"
          >
            <ArrowLeft size={24} />
          </button>
          <div className="flex-row-center gap-3">
              <HelpCircle className="icon-primary" size={32} />
              <h2>{filteredAsignaturaId ? 'Preguntas de la Asignatura' : 'Batería de Preguntas'}</h2>
          </div>
        </div>
        <button 
          onClick={() => navigate('/preguntas/nuevo', { state: { asignaturaId: filteredAsignaturaId } })}
          className="btn btn-primary"
        >
          <Plus size={20} />
          <span>Crear Pregunta</span>
        </button>
      </div>

      <div className="search-bar">
        <div className="search-filter-wrapper">
          <Search size={20} />
          <input
            type="text"
            className="search-filter-input"
            placeholder="Buscar por enunciado, tema o dificultad..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <div className="filter-hint">
            <Filter size={20} />
            <span>Filtros avanzados</span>
        </div>
      </div>

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Enunciado</th>
              <th>Tema</th>
              <th>Dificultad</th>
              <th className="text-center">Acciones</th>
            </tr>
          </thead>
          <tbody>
            {filteredPreguntas.map((pregunta) => (
              <tr key={pregunta.id}>
                <td className="truncate-cell" title={pregunta.enunciado}>
                    {pregunta.enunciado}
                </td>
                <td>
                    <span className="badge-info">
                        {pregunta.tema}
                    </span>
                </td>
                <td>
                  <span className={`badge ${getDificultadClass(pregunta.dificultad)}`}>
                    {pregunta.dificultad}
                  </span>
                </td>
                <td className="action-btns">
                  <button 
                    onClick={() => navigate(`/preguntas/editar/${pregunta.id}`, { state: { asignaturaId: filteredAsignaturaId } })}
                    className="btn-icon" 
                    title="Editar"
                  >
                    <Edit size={18} />
                  </button>
                  <button 
                    onClick={() => handleDelete(pregunta.id)}
                    className="btn-icon text-danger" 
                    title="Eliminar"
                  >
                    <Trash2 size={18} />
                  </button>
                </td>
              </tr>
            ))}
            {filteredPreguntas.length === 0 && (
              <tr>
                <td colSpan={4} className="text-center py-8 text-muted">
                  No se encontraron preguntas que coincidan con la búsqueda.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default PreguntaList;
