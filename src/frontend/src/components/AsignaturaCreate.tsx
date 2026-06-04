import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createAsignatura } from '../services/asignatura.service';
import { getGrados } from '../services/grado.service';
import type { Grado } from '../services/grado.service';
import { ArrowLeft, Save, BookOpen } from 'lucide-react';

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
    <div className="min-h-screen bg-gray-50 p-4 md:p-8">
      <div className="max-w-2xl mx-auto">
        <div className="flex items-center gap-4 mb-8">
          <button 
            onClick={() => navigate('/asignaturas')}
            className="p-2 hover:bg-gray-200 rounded-full transition-colors"
          >
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-3xl font-bold text-gray-800">Añadir Nueva Asignatura</h1>
        </div>

        <div className="bg-white rounded-xl shadow-md border border-gray-200 overflow-hidden">
          <div className="p-6 bg-blue-600 text-white flex items-center gap-3">
            <BookOpen size={24} />
            <h2 className="text-xl font-semibold">Datos de la Asignatura</h2>
          </div>

          <form onSubmit={handleSubmit} className="p-6 space-y-6">
            {error && (
              <div className="bg-red-50 text-red-600 p-4 rounded-lg border border-red-100 text-sm">
                {error}
              </div>
            )}

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Código</label>
                <input
                  type="text"
                  name="codigo"
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                  value={asignatura.codigo}
                  onChange={handleChange}
                  placeholder="Ej: ISW1, CALC1..."
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Curso Académico</label>
                <input
                  type="text"
                  name="cursoAcademico"
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                  value={asignatura.cursoAcademico}
                  onChange={handleChange}
                  placeholder="Ej: 2025-2026"
                />
              </div>

              <div className="md:col-span-2">
                <label className="block text-sm font-medium text-gray-700 mb-2">Título</label>
                <input
                  type="text"
                  name="titulo"
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                  value={asignatura.titulo}
                  onChange={handleChange}
                  placeholder="Ej: Ingeniería de Software 1"
                />
              </div>

              <div className="md:col-span-2">
                <label className="block text-sm font-medium text-gray-700 mb-2">Grado</label>
                <select
                  name="gradoId"
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none bg-white"
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
                {loadingGrados && <p className="text-xs text-gray-500 mt-1">Cargando grados...</p>}
              </div>
            </div>

            <div className="flex justify-end pt-4">
              <button
                type="submit"
                disabled={loading || loadingGrados}
                className="flex items-center gap-2 bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors shadow-md disabled:bg-blue-400"
              >
                <Save size={20} />
                <span>{loading ? 'Guardando...' : 'Guardar Asignatura'}</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AsignaturaCreate;
