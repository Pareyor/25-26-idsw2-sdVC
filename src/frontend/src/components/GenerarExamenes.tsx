import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import * as asignaturaService from '../services/asignatura.service';
import * as gradoService from '../services/grado.service';
import examenService from '../services/examen.service';

const GenerarExamenes: React.FC = () => {
  const navigate = useNavigate();
  const [asignaturas, setAsignaturas] = useState<any[]>([]);
  const [grados, setGrados] = useState<any[]>([]);
  
  const [config, setConfig] = useState<any>({
    asignaturaId: '',
    evaluacion: 'PARCIAL_1',
    temas: ['TEORIA'],
    numPreguntas: 10,
    configuracionesGrado: []
  });

  useEffect(() => {
    asignaturaService.getAsignaturas().then(res => setAsignaturas(res.data));
    gradoService.getGrados().then(res => setGrados(res.data));
  }, []);

  const handleAsignaturaChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const asignaturaId = e.target.value;
    setConfig({ ...config, asignaturaId, configuracionesGrado: [] });
  };

  const addGradoConfig = (gradoId: string) => {
    if (!gradoId) return;
    
    // Evitar duplicados
    if (config.configuracionesGrado.find((c: any) => c.gradoId === parseInt(gradoId))) {
      alert("Este grado ya ha sido añadido.");
      return;
    }

    const newConfig = {
      gradoId: parseInt(gradoId),
      numExamenes: '',
      numTipos: '',
      proporcionFacil: '',
      proporcionMedia: '',
      proporcionDificil: ''
    };
    setConfig({ ...config, configuracionesGrado: [...config.configuracionesGrado, newConfig] });
  };

  const updateGradoConfig = (index: number, field: string, value: number) => {
    const newConfigs = [...config.configuracionesGrado];
    newConfigs[index][field] = value;
    setConfig({ ...config, configuracionesGrado: newConfigs });
  };

  const removeGradoConfig = (index: number) => {
    const newConfigs = config.configuracionesGrado.filter((_: any, i: number) => i !== index);
    setConfig({ ...config, configuracionesGrado: newConfigs });
  };

  const handleCancel = async () => {
    try {
      await examenService.cancelarGeneracion();
    } catch (error) {
      console.error("Error al cancelar la generación:", error);
    } finally {
      navigate('/dashboard');
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await examenService.generarExamenes(config);
      alert('Exámenes generados con éxito');
      navigate('/dashboard');
    } catch (error) {
      console.error(error);
      alert('Error al generar exámenes: ' + (error as any).response?.data?.message || 'Error desconocido');
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-6">Generar Exámenes</h1>
      <form onSubmit={handleSubmit} className="space-y-6">
        <div>
          <label className="block mb-2">Asignatura:</label>
          <select onChange={handleAsignaturaChange} className="border p-2 w-full">
            <option value="">Seleccione una asignatura</option>
            {asignaturas.map(a => <option key={a.id} value={a.id}>{a.titulo}</option>)}
          </select>
        </div>

        {config.asignaturaId && (
          <div>
            <label className="block mb-2">Añadir Grado a la configuración:</label>
            <select onChange={(e) => addGradoConfig(e.target.value)} className="border p-2 w-full">
              <option value="">Seleccione un grado</option>
              {grados.map(g => <option key={g.id} value={g.id}>{g.titulo}</option>)}
            </select>
          </div>
        )}

        {config.configuracionesGrado.map((cfg: any, index: number) => {
          const grado = grados.find(g => g.id === cfg.gradoId);
          return (
            <div key={index} className="border p-4 mt-4 bg-gray-50 flex items-center justify-between">
              <div>
                <h3 className="font-bold mb-2">Grado: {grado ? grado.titulo : cfg.gradoId}</h3>
                <div className="flex gap-2 items-center">
                  <input type="number" placeholder="Núm Examenes" value={cfg.numExamenes} onChange={(e) => updateGradoConfig(index, 'numExamenes', parseInt(e.target.value))} className="border p-1 w-24" />
                  <input type="number" placeholder="Núm Tipos" value={cfg.numTipos} onChange={(e) => updateGradoConfig(index, 'numTipos', parseInt(e.target.value))} className="border p-1 w-24" />
                  <input type="number" placeholder="% Fácil" value={cfg.proporcionFacil} onChange={(e) => updateGradoConfig(index, 'proporcionFacil', parseInt(e.target.value))} className="border p-1 w-20" />
                  <input type="number" placeholder="% Media" value={cfg.proporcionMedia} onChange={(e) => updateGradoConfig(index, 'proporcionMedia', parseInt(e.target.value))} className="border p-1 w-20" />
                  <input type="number" placeholder="% Difícil" value={cfg.proporcionDificil} onChange={(e) => updateGradoConfig(index, 'proporcionDificil', parseInt(e.target.value))} className="border p-1 w-20" />
                </div>
              </div>
              <button type="button" onClick={() => removeGradoConfig(index)} className="bg-red-500 text-white p-2 rounded hover:bg-red-600">Eliminar</button>
            </div>

          );
        })}

        <div className="pt-4">
          <button type="submit" className="bg-blue-600 text-white p-3 rounded">Generar Exámenes</button>
          <button type="button" onClick={handleCancel} className="bg-gray-500 text-white p-3 rounded ml-2">Cancelar</button>
        </div>
      </form>
    </div>
  );
};

export default GenerarExamenes;
