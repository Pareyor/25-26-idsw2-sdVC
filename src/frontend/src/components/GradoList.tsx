import React, { useEffect, useState } from 'react';
import { getGrados, deleteGrado } from '../services/grado.service';
import type { Grado } from '../services/grado.service';
import { Search, Plus, Edit, Trash2, ArrowLeft } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { normalizeString } from '../utils/searchUtils';
import './Listas.css';

const GradoList: React.FC = () => {
  const [grados, setGrados] = useState<Grado[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchGrados();
  }, []);

  const fetchGrados = async () => {
    try {
      const response = await getGrados();
      setGrados(response.data);
      setLoading(false);
    } catch (err) {
      setError('Error al cargar los grados');
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('¿Estás seguro de que deseas eliminar este grado? Esta acción no se puede deshacer.')) {
      try {
        await deleteGrado(id);
        setGrados(grados.filter(g => g.id !== id));
      } catch (err) {
        alert('Error al eliminar el grado. Es posible que tenga asignaturas vinculadas.');
      }
    }
  };

  const filteredGrados = grados.filter(grado => {
    const term = normalizeString(searchTerm);
    return normalizeString(grado.titulo).includes(term) ||
           normalizeString(grado.codigo).includes(term);
  });

  if (loading) return <div className="loading-state">Cargando grados...</div>;
  if (error) return <div className="error-message">{error}</div>;

  return (
    <div className="list-container">
      <div className="list-header">
        <div className="flex-row-center gap-4">
          <button 
            onClick={() => navigate('/dashboard')}
            className="btn-icon"
            title="Volver"
          >
            <ArrowLeft size={24} />
          </button>
          <h2>Gestión de Grados</h2>
        </div>
        <button 
          onClick={() => navigate('/grados/nuevo')}
          className="btn btn-primary"
        >
          <Plus size={20} />
          <span>Añadir Grado</span>
        </button>
      </div>

      <div className="search-bar">
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
      </div>

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Código</th>
              <th>Título</th>
              <th className="text-center">Acciones</th>
            </tr>
          </thead>
          <tbody>
            {filteredGrados.map((grado) => (
              <tr key={grado.id}>
                <td>{grado.codigo}</td>
                <td>{grado.titulo}</td>
                <td className="action-btns">
                  <button 
                    onClick={() => navigate(`/grados/editar/${grado.id}`)}
                    className="btn-icon" 
                    title="Editar"
                  >
                    <Edit size={18} />
                  </button>
                  <button 
                    onClick={() => handleDelete(grado.id)}
                    className="btn-icon text-danger" 
                    title="Eliminar"
                  >
                    <Trash2 size={18} />
                  </button>
                </td>
              </tr>
            ))}
            {filteredGrados.length === 0 && (
              <tr>
                <td colSpan={3} className="text-center py-8 text-muted">
                  No se encontraron grados que coincidan con la búsqueda.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default GradoList;
