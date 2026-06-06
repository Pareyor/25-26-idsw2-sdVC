import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getAsignatura, updateAsignatura } from '../services/asignatura.service';
import { getGrados } from '../services/grado.service';
import type { Grado } from '../services/grado.service';
import { ArrowLeft, Save, BookOpen } from 'lucide-react';
import './Formularios.css';

const AsignaturaEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [asignatura, setAsignatura] = useState({
    codigo: '',
    titulo: '',
    cursoAcademico: '',
    gradoId: 0,
  });
  const [grados, setGrados] = useState<Grado[]>([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      loadData(parseInt(id));
    }
  }, [id]);

  const loadData = async (asignaturaId: number) => {
    try {
      const [asignaturaRes, gradosRes] = await Promise.all([
        getAsignatura(asignaturaId),
        getGrados()
      ]);
      setAsignatura(asignaturaRes.data);
      setGrados(gradosRes.data);
      setLoading(false);
    } catch (err: any) {
      setError('Error al cargar los datos.');
      setLoading(false);
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

    setSaving(true);
    setError('');

    try {
      await updateAsignatura(parseInt(id!), asignatura as any);
      navigate('/asignaturas');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al actualizar la asignatura.');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-main)' }}>Cargando datos de la asignatura...</div>;

  return (
    <div className="form-container">
      <div className="form-header" style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '2rem' }}>
        <button 
          onClick={() => navigate('/asignaturas')}
          className="btn-icon"
        >
          <ArrowLeft size={24} />
        </button>
        <h1>Editar Asignatura</h1>
      </div>

      <form onSubmit={handleSubmit} className="standard-form">
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', marginBottom: '1rem' }}>
          <BookOpen size={24} color="var(--primary)" />
          <h2 style={{ margin: 0, textAlign: 'left', fontSize: '1.5rem' }}>Datos de: {asignatura.titulo}</h2>
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
          />
        </div>

        <div className="form-group">
          <label>Grado</label>
          <select
            name="gradoId"
            required
            value={asignatura.gradoId || 0}
            onChange={handleChange}
          >
            <option value={0}>Seleccione un grado...</option>
            {grados.map(grado => (
              <option key={grado.id} value={grado.id}>
                [{grado.codigo}] {grado.titulo}
              </option>
            ))}
          </select>
        </div>

        <div className="form-actions">
          <button
            type="submit"
            disabled={saving}
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
            <span>{saving ? 'Guardando...' : 'Actualizar Asignatura'}</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default AsignaturaEdit;
