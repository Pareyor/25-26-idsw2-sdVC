import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createAsignatura } from '../services/asignatura.service';
import { getGrados } from '../services/grado.service';
import type { Grado } from '../services/grado.service';
import { ArrowLeft, Save, BookOpen } from 'lucide-react';
import './Formularios.css';

const AsignaturaCreate: React.FC = () => {
  const [asignatura, setAsignatura] = useState({
    codigo: '',
    titulo: '',
    cursoAcademico: '',
    gradoId: 0,
  });
  const [grados, setGrados] = useState<Grado[]>([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [loadingGrados, setLoadingGrados] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchGrados();
  }, []);

  const fetchGrados = async () => {
    try {
      const response = await getGrados();
      setGrados(response.data);
      setLoadingGrados(false);
    } catch (err) {
      setError('Error al cargar la lista de grados.');
      setLoadingGrados(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setAsignatura(prev => ({ 
      ...prev, 
      [name]: name === 'gradoId' ? parseInt(value) : value 
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (asignatura.gradoId === 0) {
      setError('Debe seleccionar un grado.');
      return;
    }

    setLoading(true);
    setError('');

    try {
      await createAsignatura(asignatura);
      navigate('/asignaturas');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al crear la asignatura. Verifique el código.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <div className="form-header" style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '2rem' }}>
        <button 
          onClick={() => navigate('/asignaturas')}
          className="btn-icon"
        >
          <ArrowLeft size={24} />
        </button>
        <h1>Añadir Nueva Asignatura</h1>
      </div>

      <form onSubmit={handleSubmit} className="standard-form">
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', marginBottom: '1rem' }}>
          <BookOpen size={24} color="var(--primary)" />
          <h2 style={{ margin: 0, textAlign: 'left', fontSize: '1.5rem' }}>Datos de la Asignatura</h2>
        </div>

        {error && (
          <div className="error-message" style={{ backgroundColor: 'rgba(244, 63, 94, 0.1)', color: 'var(--accent)', padding: '1rem', borderRadius: '8px', border: '1px solid var(--accent)', marginBottom: '1rem' }}>
            {error}
          </div>
        )}

        <div className="form-group">
          <label>Código</label>
          <input
            type="text"
            name="codigo"
            required
            value={asignatura.codigo}
            onChange={handleChange}
            placeholder="Ej: ISW1, CALC1..."
          />
        </div>

        <div className="form-group">
          <label>Curso Académico</label>
          <input
            type="text"
            name="cursoAcademico"
            required
            value={asignatura.cursoAcademico}
            onChange={handleChange}
            placeholder="Ej: 2025-2026"
          />
        </div>

        <div className="form-group">
          <label>Título</label>
          <input
            type="text"
            name="titulo"
            required
            value={asignatura.titulo}
            onChange={handleChange}
            placeholder="Ej: Ingeniería de Software 1"
          />
        </div>

        <div className="form-group">
          <label>Grado</label>
          <select
            name="gradoId"
            required
            value={asignatura.gradoId}
            onChange={handleChange}
            disabled={loadingGrados}
          >
            <option value={0}>Seleccione un grado...</option>
            {grados.map(grado => (
              <option key={grado.id} value={grado.id}>
                [{grado.codigo}] {grado.titulo}
              </option>
            ))}
          </select>
          {loadingGrados && <p style={{ fontSize: '0.75rem', color: 'var(--text-muted)', marginTop: '0.25rem' }}>Cargando grados...</p>}
        </div>

        <div className="form-actions">
          <button
            type="submit"
            disabled={loading || loadingGrados}
            className="btn btn-primary"
            style={{ 
              display: 'flex', 
              alignItems: 'center', 
              gap: '0.5rem', 
              padding: '0.75rem 1.5rem', 
              borderRadius: '12px', 
              backgroundColor: 'var(--primary)', 
              color: 'white', 
              border: 'none', 
              cursor: 'pointer' 
            }}
          >
            <Save size={20} />
            <span>{loading ? 'Guardando...' : 'Guardar Asignatura'}</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default AsignaturaCreate;
