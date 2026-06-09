import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createPregunta } from '../services/pregunta.service';
import { getAsignaturas } from '../services/asignatura.service';
import type { Asignatura } from '../services/asignatura.service';
import { Dificultad } from '../types/pregunta';
import type { Respuesta } from '../types/pregunta';
import { ArrowLeft, Save, PlusCircle, Trash2, HelpCircle } from 'lucide-react';
import './Formularios.css';

const PreguntaCreate: React.FC = () => {
  const [pregunta, setPregunta] = useState({
    enunciado: '',
    tema: '',
    dificultad: Dificultad.FACIL,
    asignaturaId: 0,
    respuestas: [] as Respuesta[],
  });
  const [asignaturas, setAsignaturas] = useState<Asignatura[]>([]);
  const [nuevaRespuesta, setNuevaRespuesta] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [loadingAsignaturas, setLoadingAsignaturas] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchAsignaturas();
  }, []);

  const fetchAsignaturas = async () => {
    try {
      const response = await getAsignaturas();
      setAsignaturas(response.data);
      setLoadingAsignaturas(false);
    } catch (err) {
      setError('Error al cargar la lista de asignaturas.');
      setLoadingAsignaturas(false);
    }
  };

  const handleAddRespuesta = () => {
    if (!nuevaRespuesta.trim()) return;
    setPregunta(prev => ({
      ...prev,
      respuestas: [...prev.respuestas, { opcion: nuevaRespuesta, esCorrecta: false }]
    }));
    setNuevaRespuesta('');
  };

  const handleToggleCorrecta = (index: number) => {
    setPregunta(prev => ({
      ...prev,
      respuestas: prev.respuestas.map((r, i) => i === index ? { ...r, esCorrecta: !r.esCorrecta } : r)
    }));
  };

  const handleRemoveRespuesta = (index: number) => {
    setPregunta(prev => ({
      ...prev,
      respuestas: prev.respuestas.filter((_, i) => i !== index)
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (pregunta.asignaturaId === 0) {
      setError('Debe seleccionar una asignatura.');
      return;
    }
    if (pregunta.respuestas.length === 0) {
      setError('Debe añadir al menos una respuesta.');
      return;
    }
    if (!pregunta.respuestas.some(r => r.esCorrecta)) {
      setError('Debe marcar al menos una respuesta como correcta.');
      return;
    }

    setLoading(true);
    setError('');

    try {
      await createPregunta(pregunta);
      navigate('/preguntas');
    } catch (err: any) {
      setError('Error al crear la pregunta.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container" style={{maxWidth: '800px'}}>
      <div className="form-header-actions">
        <button 
          onClick={() => navigate('/preguntas')}
          className="btn-icon"
          title="Volver"
        >
          <ArrowLeft size={24} />
        </button>
      </div>

      <h2>Añadir Nueva Pregunta</h2>

      <form onSubmit={handleSubmit} className="standard-form">
        {error && <div className="error-message">{error}</div>}

        <div className="form-group">
          <label>Asignatura</label>
          <select
            name="asignaturaId"
            required
            value={pregunta.asignaturaId}
            onChange={(e) => setPregunta({...pregunta, asignaturaId: parseInt(e.target.value)})}
            disabled={loadingAsignaturas}
          >
            <option value={0}>Seleccione una asignatura...</option>
            {asignaturas.map(asig => (
              <option key={asig.id} value={asig.id}>
                [{asig.codigo}] {asig.titulo}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Enunciado</label>
          <textarea
            required
            value={pregunta.enunciado}
            onChange={(e) => setPregunta({...pregunta, enunciado: e.target.value})}
            placeholder="Escriba el enunciado de la pregunta..."
            rows={3}
          />
        </div>

        <div className="form-row">
          <div className="form-group flex-1">
            <label>Tema</label>
            <input 
              type="text"
              value={pregunta.tema} 
              onChange={(e) => setPregunta({...pregunta, tema: e.target.value})}
              placeholder="Ej: SQL, POO, Requisitos..."
              required
            />
          </div>
          <div className="form-group flex-1">
            <label>Dificultad</label>
            <select value={pregunta.dificultad} onChange={(e) => setPregunta({...pregunta, dificultad: e.target.value as Dificultad})}>
              {Object.values(Dificultad).map(d => <option key={d} value={d}>{d}</option>)}
            </select>
          </div>
        </div>

        <div className="form-group">
          <label>Respuestas (Añadir al menos una correcta)</label>
          <div className="input-with-button">
            <input
              type="text"
              value={nuevaRespuesta}
              onChange={(e) => setNuevaRespuesta(e.target.value)}
              placeholder="Escriba una opción..."
              onKeyPress={(e) => e.key === 'Enter' && (e.preventDefault(), handleAddRespuesta())}
            />
            <button type="button" onClick={handleAddRespuesta} className="btn-icon">
              <PlusCircle />
            </button>
          </div>
          <div className="items-list">
            {pregunta.respuestas.map((r, i) => (
              <div key={i} className={`list-item ${r.esCorrecta ? 'item-success' : ''}`}>
                <input 
                  type="checkbox" 
                  checked={r.esCorrecta} 
                  onChange={() => handleToggleCorrecta(i)} 
                  title="Marcar como correcta"
                />
                <span className="item-text">
                  {r.opcion}
                </span>
                <button type="button" onClick={() => handleRemoveRespuesta(i)} className="btn-icon-sm text-danger">
                  <Trash2 size={18}/>
                </button>
              </div>
            ))}
          </div>
        </div>

        <div className="form-actions">
          <button type="submit" disabled={loading || loadingAsignaturas} className="btn btn-primary">
            <Save size={20} />
            <span>{loading ? 'Guardando...' : 'Guardar Pregunta'}</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default PreguntaCreate;
