import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getPregunta, updatePregunta } from '../services/pregunta.service';
import { getAsignaturas } from '../services/asignatura.service';
import type { Asignatura } from '../services/asignatura.service';
import { Tema, Dificultad } from '../types/pregunta';
import type { Respuesta } from '../types/pregunta';
import { ArrowLeft, Save, PlusCircle, Trash2, HelpCircle } from 'lucide-react';

const PreguntaEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [pregunta, setPregunta] = useState({
    enunciado: '',
    tema: Tema.GENERAL,
    dificultad: Dificultad.FACIL,
    asignaturaId: 0,
    respuestas: [] as Respuesta[],
  });
  const [asignaturas, setAsignaturas] = useState<Asignatura[]>([]);
  const [nuevaRespuesta, setNuevaRespuesta] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      loadData(parseInt(id));
    }
  }, [id]);

  const loadData = async (preguntaId: number) => {
    try {
      const [preguntaRes, asignaturasRes] = await Promise.all([
        getPregunta(preguntaId),
        getAsignaturas()
      ]);
      setPregunta(preguntaRes.data);
      setAsignaturas(asignaturasRes.data);
      setLoading(false);
    } catch (err: any) {
      setError('Error al cargar los datos.');
      setLoading(false);
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

    setSaving(true);
    setError('');

    try {
      await updatePregunta(parseInt(id!), pregunta as any);
      navigate('/preguntas');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al actualizar la pregunta.');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="p-8 text-center">Cargando datos de la pregunta...</div>;

  return (
    <div className="min-h-screen bg-gray-50 p-4 md:p-8">
      <div className="max-w-2xl mx-auto">
        <div className="flex items-center gap-4 mb-8">
          <button 
            onClick={() => navigate('/preguntas')}
            className="p-2 hover:bg-gray-200 rounded-full transition-colors"
          >
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-3xl font-bold text-gray-800">Editar Pregunta</h1>
        </div>

        <form onSubmit={handleSubmit} className="bg-white rounded-xl shadow-md border border-gray-200 overflow-hidden">
          <div className="p-6 bg-blue-600 text-white flex items-center gap-3">
            <HelpCircle size={24} />
            <h2 className="text-xl font-semibold">Modificar Pregunta</h2>
          </div>

          <div className="p-6 space-y-6">
            {error && <div className="bg-red-50 text-red-600 p-4 rounded-lg text-sm">{error}</div>}

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Asignatura</label>
              <select
                name="asignaturaId"
                required
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none bg-white"
                value={pregunta.asignaturaId || 0}
                onChange={(e) => setPregunta({...pregunta, asignaturaId: parseInt(e.target.value)})}
              >
                <option value={0}>Seleccione una asignatura...</option>
                {asignaturas.map(asig => (
                  <option key={asig.id} value={asig.id}>
                    [{asig.codigo}] {asig.titulo}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Enunciado</label>
              <textarea
                required
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                value={pregunta.enunciado}
                onChange={(e) => setPregunta({...pregunta, enunciado: e.target.value})}
                placeholder="Escriba el enunciado de la pregunta..."
                rows={3}
              />
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Tema</label>
                <select className="w-full px-4 py-2 border border-gray-300 rounded-lg" value={pregunta.tema} onChange={(e) => setPregunta({...pregunta, tema: e.target.value as Tema})}>
                  {Object.values(Tema).map(t => <option key={t} value={t}>{t}</option>)}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Dificultad</label>
                <select className="w-full px-4 py-2 border border-gray-300 rounded-lg" value={pregunta.dificultad} onChange={(e) => setPregunta({...pregunta, dificultad: e.target.value as Dificultad})}>
                  {Object.values(Dificultad).map(d => <option key={d} value={d}>{d}</option>)}
                </select>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Respuestas (Gestión Integral)</label>
              <div className="flex gap-2 mb-4">
                <input
                  type="text"
                  className="flex-grow px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                  value={nuevaRespuesta}
                  onChange={(e) => setNuevaRespuesta(e.target.value)}
                  placeholder="Escriba una opción..."
                  onKeyPress={(e) => e.key === 'Enter' && (e.preventDefault(), handleAddRespuesta())}
                />
                <button type="button" onClick={handleAddRespuesta} className="bg-gray-200 p-2 rounded-lg hover:bg-gray-300 transition-colors">
                  <PlusCircle />
                </button>
              </div>
              <div className="space-y-2">
                {pregunta.respuestas.map((r, i) => (
                  <div key={i} className="flex items-center gap-3 p-3 border rounded-lg hover:bg-gray-50 transition-colors group">
                    <input 
                      type="checkbox" 
                      className="w-5 h-5 text-blue-600 rounded focus:ring-blue-500"
                      checked={r.esCorrecta} 
                      onChange={() => handleToggleCorrecta(i)} 
                      title="Marcar como correcta"
                    />
                    <span className={`flex-grow ${r.esCorrecta ? 'font-bold text-green-700' : 'text-gray-700'}`}>
                      {r.opcion}
                    </span>
                    <button type="button" onClick={() => handleRemoveRespuesta(i)} className="text-red-500 opacity-0 group-hover:opacity-100 transition-opacity">
                      <Trash2 size={18}/>
                    </button>
                  </div>
                ))}
              </div>
            </div>

            <div className="flex justify-end pt-4 border-t">
              <button type="submit" disabled={saving} className="flex items-center gap-2 bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors disabled:bg-blue-300">
                <Save size={20} />
                <span>{saving ? 'Guardando...' : 'Actualizar Pregunta'}</span>
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default PreguntaEdit;
