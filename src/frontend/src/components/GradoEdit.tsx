import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getGrado, updateGrado } from '../services/grado.service';
import type { Grado } from '../services/grado.service';
import { ArrowLeft, Save, GraduationCap } from 'lucide-react';
import './Formularios.css';

const GradoEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [grado, setGrado] = useState<Grado | null>(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      fetchGrado(parseInt(id));
    }
  }, [id]);

  const fetchGrado = async (gradoId: number) => {
    try {
      const response = await getGrado(gradoId);
      setGrado(response.data);
      setLoading(false);
    } catch (err: any) {
      setError('Error al cargar los datos del grado.');
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!grado) return;
    const { name, value } = e.target;
    setGrado(prev => prev ? ({ ...prev, [name]: value }) : null);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!grado || !id) return;
    
    setSaving(true);
    setError('');

    try {
      await updateGrado(parseInt(id), grado);
      navigate('/grados');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al actualizar el grado.');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="loading-state">Cargando datos del grado...</div>;

  return (
    <div className="form-container">
      <div className="form-header-actions">
        <button 
          onClick={() => navigate('/grados')}
          className="btn-icon"
          title="Volver"
        >
          <ArrowLeft size={24} />
        </button>
      </div>

      <h2>Editar Grado</h2>

      {grado && (
        <form onSubmit={handleSubmit} className="standard-form">
          {error && (
            <div className="error-message">
              {error}
            </div>
          )}

          <div className="form-group">
            <label>Código del Grado</label>
            <input
              type="text"
              name="codigo"
              required
              value={grado.codigo}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label>Título del Grado</label>
            <input
              type="text"
              name="titulo"
              required
              value={grado.titulo}
              onChange={handleChange}
            />
          </div>

          <div className="form-actions">
            <button
              type="submit"
              disabled={saving}
              className="btn btn-primary"
            >
              <Save size={20} />
              <span>{saving ? 'Guardando...' : 'Actualizar Grado'}</span>
            </button>
          </div>
        </form>
      )}
    </div>
  );
};

export default GradoEdit;
